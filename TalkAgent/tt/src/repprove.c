/*
 * ThoughtTreasure
 * Copyright 1996, 1997 Erik Thomas Mueller. All Rights Reserved.
 * See <URL:../ttlegal.txt> for legal details.
 * Bugs to <URL:mailto:bugs@signiform.com>.
 *
 * 19940821: begun
 * 19940822: debugging
 */

#include "tt.h"
#include "repbasic.h"
#include "repdb.h"
#include "repobj.h"
#include "repobjl.h"
#include "repprove.h"
#include "repspace.h"
#include "reptime.h"
#include "utildbg.h"

/* Proof */

ProofReason *ProofReasonCreate(Proof *proof, ProofReason *next)
{
  ProofReason	*pr;
  pr = CREATE(ProofReason);
  pr->proof = proof;
  pr->next = next;
  return(pr);
}

ProofReason *ProofReasonLast(ProofReason *reasons)
{
  while (reasons->next) reasons = reasons->next;
  return(reasons);
}

ProofReason *ProofReasonAppend(ProofReason *pr1, ProofReason *pr2)
{
  if (pr1) {
    ProofReasonLast(pr1)->next = pr2;
    return(pr1);
  } else return(pr2);
}

ProofReason *ProofReasonCopyAll(ProofReason *pr)
{
  ProofReason	*r;
  r = NULL;
  for (; pr; pr = pr->next) {
    r = ProofReasonAppend(r, ProofReasonCreate(pr->proof, NULL));
  }
  return(r);
}

void ProofReasonFree(ProofReason *pr)
{
  ProofReason *n;
  while (pr) {
    n = pr->next;
    MemFree(pr, "ProofReason");
    pr = n;
  }
}

Proof *ProofCreate(Obj *fact, Bd *bd, Obj *rule, ProofReason *reasons,
                   Proof *next)
{
  Proof	*p;
  p = CREATE(Proof);
  p->fact = fact;
  p->bd = bd;
  p->rule = rule;
  p->reasons = reasons;
  p->next = next;
  return(p);
}

Proof *ProofCopyHead(Proof *p)
{
  return(ProofCreate(p->fact, BdCopy(p->bd), p->rule, p->reasons, NULL));
}

void ProofFree(Proof *p)
{
  Proof *n;
  while (p) {
    ProofReasonFree(p->reasons);
    BdFree(p->bd);
    n = p->next;
    MemFree(p, "Proof");
    p = n;
  }
}

Proof *ProofAddLevel(Obj *fact, Obj *rule, Proof *proof, Proof *r)
{
  ProofReason	*pr;
  Proof	*p;
  for (p = proof; p; p = p->next) {
    pr = ProofReasonCreate(ProofCopyHead(p), NULL);
    r = ProofCreate(fact, BdCopy(p->bd), rule, pr, r);
  }
  return(r);
}

void ProofPrintAll1(FILE *stream, Proof *p, int top)
{
  for (; p; p = p->next) {
    if (top) {
      IndentInit();
      StreamSep(stream);
    }
    ProofPrint(stream, p);
  }
}

void ProofReasonPrintAll(FILE *stream, ProofReason *pr)
{
  for (; pr; pr = pr->next) ProofPrintAll1(stream, pr->proof, 0);
}

void ProofPrint(FILE *stream, Proof *p)
{
  IndentUp();
  IndentPrint(stream);
  ObjPrint(stream, p->fact);
  fputs(" because ", stream);
  ObjPrint(stream, p->rule);
  fputc(NEWLINE, stream);
  BdPrint(stream, p->bd);
  ProofReasonPrintAll(stream, p->reasons);
  IndentDown();
}

void ProofPrintAll(FILE *stream, Proof *p)
{
  ProofPrintAll1(stream, p, 1);
}

Proof *ProofAppendEach(Proof *head, Proof *tails, Proof *r)
{
  ProofReason	*reasons;
  Proof			*tail;
  for (tail = tails; tail; tail = tail->next) {
    reasons = ProofReasonAppend(ProofReasonCopyAll(head->reasons),
                                ProofReasonCopyAll(tail->reasons));
    r = ProofCreate(head->fact, BdCopyAppend(head->bd, tail->bd), head->rule,
                    reasons, r);
  }
  return(r);
}

/* Prove. */

Bool ProveAnd(Ts *ts, TsRange *tsr, Obj *goal, ObjList *rules, ObjList *more,
              Bool querydb, /* RESULTS */ Proof **out_pr)
{
  int	i, len;
  Obj	*obj;
  Proof	*p, *proof_r, *proof1, *proof2;
  if (!Prove1(ts, tsr, I(goal, 1), rules, more, querydb, &proof_r)) return(0);
  proof_r = ProofAddLevel(goal, N("and"), proof_r, NULL);
  proof2 = NULL;
  for (i = 2, len = ObjLen(goal); i < len; i++) {
    proof2 = NULL;
    for (p = proof_r; p; p = p->next) {
      obj = ObjInstan(I(goal, i), p->bd);	/* todoFREE */
      if (Prove1(ts, tsr, obj, rules, more, querydb, &proof1)) {
        proof2 = ProofAppendEach(p,
                   ProofAddLevel(goal, N("and"), proof1, NULL), proof2);
        ProofFree(proof1);
      }
    }
    ProofFree(proof_r);
    if (!proof2) return(0);
    proof_r = proof2;
  }
  *out_pr = proof_r;
  return(1);
}

Bool ProveOr(Ts *ts, TsRange *tsr, Obj *goal, ObjList *rules, ObjList *more,
             Bool querydb, /* RESULTS */ Proof **out_pr)
{
  int	i, len;
  Proof	*proof_r, *proof1;
  proof_r = NULL;
  for (i = 1, len = ObjLen(goal); i < len; i++) {
    if (Prove1(ts, tsr, I(goal, i), rules, more, querydb, &proof1)) {
      proof_r = ProofAddLevel(goal, N("or"), proof1, proof_r);
      ProofFree(proof1);
    }
  }
  *out_pr = proof_r;
  return(1);
}

Bool ProveNot(Ts *ts, TsRange *tsr, Obj *goal, ObjList *rules, ObjList *more,
              Bool querydb, /* RESULTS */ Proof **out_pr)
{
  Proof	*proof1;
  if (Prove1(ts, tsr, I(goal, 1), rules, more, querydb, &proof1)) {
    ProofFree(proof1);
    return(0);
  }
  *out_pr = ProofCreate(goal, BdCreate(), N("not"), NULL, NULL);
  return(1);
}

/* todo: Add symmetric relations, opposites. Cycle prevention.
 * todo: Not sure of BdCopy correctness.
 * todoFREE: Lots of freeing to do.
 */
Bool ProveFact(Ts *ts, TsRange *tsr, Obj *goal, ObjList *rules, ObjList *more,
               Bool querydb, /* RESULTS */ Proof **out_pr)
{
  int		i;
  Bd		*new_bd;
  Obj		*head, *obj, *prop, *goal1;
  ObjList	*matches, *p, *objs;
  TsRange	tsr1;
  Proof		*proof_r, *proof1;

  if (!goal) return(0);
  if (ISA(N("grid-traversal-noncanonical"), I(goal, 0))) {
    prop = DbGetRelationValue(&TsNA, NULL, N("canonical-of"), I(goal, 0),
                              I(goal, 0));
    /* todo: Resolve "the street". */
    goal = L(prop, I(goal, 1), E);
  }

  proof_r = NULL;
  if (querydb && N("location-of") == I(goal, 0)) {
    if (SpaceLocatedAt(ts, tsr, I(goal, 1), I(goal, 2), &tsr1)) {
      goal1 = ObjCopyList(goal);
      ObjSetTsRange(goal1, &tsr1);
      proof_r = ProofCreate(goal1, BdCreate(), N("true"), NULL, proof_r);
    }
  }
  if (querydb && N("near") == I(goal, 0)) {
    if ((objs = SpaceFindNear(ts, tsr, I(goal, 1), I(goal, 2), NULL))) {
      proof_r = ProofCreate(goal, BdCreate(), N("true"), NULL, proof_r);
      ObjListFree(objs);
    } else if ((objs = SpaceFindNear(ts, tsr, I(goal, 2), I(goal, 1), NULL))) {
    /* Is this really necessary? near should be symmetric */
      proof_r = ProofCreate(goal, BdCreate(), N("true"), NULL, proof_r);
      ObjListFree(objs);
    }
  }
  for (i = 0; i < ObjLen(goal); i++) {
    if (querydb && (matches = DbRetrieveDesc(ts, tsr, goal, i, 0, 0))) {
      for (p = matches; p; p = p->next) {
        if ((new_bd = ObjUnify2(goal, p->obj, BdCreate()))) {
          proof_r = ProofCreate(p->obj, new_bd, N("true"), NULL, proof_r);
        } else {
          Dbg(DBGOBJ, DBGBAD, "ProveFact unification failure:");
          ObjPrettyPrint(Log, goal);
          ObjPrettyPrint(Log, p->obj);
        }
      }
      ObjListFree(matches);
      break;
        /* If we don't break, then we have to eliminate duplicates. */
    }
  }
  /* todo: Add other kinds of near. */
  head = I(goal, 0);
  if (head == N("near-audible")) {
    if (N("?human") == I(goal, 1)) {
    /* todo: Generalize this. */
      matches = SpaceFindNearAudible(ts, tsr, N("human"), I(goal, 2));
      for (p = matches; p; p = p->next) {
        new_bd = BdAssign(BdCreate(), I(goal, 1), p->obj);
        proof_r = ProofCreate(goal, new_bd, N("true"), NULL, proof_r);
      }
    }
  }

  for (p = more; p; p = p->next) {
    if ((new_bd = ObjUnify2(p->obj, goal, BdCreate()))) {
      proof_r = ProofCreate(goal, new_bd, N("true"), NULL, proof_r);
    }
  }
  for (p = rules; p; p = p->next) {
    if ((new_bd = ObjUnify2(I(p->obj, 2), goal, BdCreate()))) {
      obj = ObjInstan(I(p->obj, 1), new_bd);	/* todoFREE: obj */
      if (Prove1(ts, tsr, obj, rules, more, querydb, &proof1)) {
        proof_r = ProofAddLevel(goal, p->obj, proof1, proof_r);
        ProofFree(proof1);
      }
    }
  }
  if (proof_r) {
    *out_pr = proof_r;
    return(1);
  }
  return(0);
}

Bool Prove1(Ts *ts, TsRange *tsr, Obj *goal, ObjList *rules, ObjList *more,
            Bool querydb, /* RESULTS */ Proof **out_pr)
{
  Obj	*head;
  head = I(goal, 0);
  if (N("and") == head) {
    return(ProveAnd(ts, tsr, goal, rules, more, querydb, out_pr));
  } else if (N("or") == head) {
    return(ProveOr(ts, tsr, goal, rules, more, querydb, out_pr));
  } else if (N("not") == head) {
    return(ProveNot(ts, tsr, goal, rules, more, querydb, out_pr));
  } else return(ProveFact(ts, tsr, goal, rules, more, querydb, out_pr));
}

ObjList *ProofRules;

Bool Prove(Ts *ts, TsRange *tsr, Obj *goal, ObjList *more, Bool querydb,
           /* RESULTS */ Proof **out_pr)
{
  if (Prove1(ts, tsr, goal, ProofRules, more, querydb, out_pr)) {
    if (DbgOn(DBGOBJ, DBGHYPER)) {
      fprintf(Log, "found proofs of ");
      ObjPrint(Log, goal);
      fputc(NEWLINE, Log);
      ProofPrintAll(Log, *out_pr);
    }
    return(1);
  }
  return(0);
}

ObjList *ProveRetrieve(Ts *ts, TsRange *tsr, Obj *ptn, Bool freeptn)
{
  ObjList	*r;
  Proof		*proofs, *pr;
  ptn = ObjCopyWithoutWeight(ptn);	/* todo */
  if (!Prove(ts, tsr, ptn, NULL, 1, &proofs)) return(NULL);
  r = NULL;
  for (pr = proofs; pr; pr = pr->next) {
    r = ObjListCreate(pr->fact, r);
  }
  ProofFree(proofs);
  return(r);
}

void ProverTool()
{
  char  	buf[DWORDLEN];
  Obj   	*obj;
  ObjList       *objs;
  Ts    	ts;
  printf("Welcome to the prover.\n");
  while (1) {
    if (!StreamAskStd("Enter timestamp (?=wildcard): ", DWORDLEN, buf)) return;
    if (!TsParse(&ts, buf)) continue;
    printf("Enter query pattern: ");
    if ((obj = ObjRead(stdin))) {
      fprintf(stdout, "query pattern: ");
      fputc(TREE_TS_SLOT, stdout);
      TsPrint(stdout, &ts);
      fputc(TREE_SLOT_SEP, stdout);
      ObjPrint(stdout, obj);
      fprintf(stdout, "\n");
      fprintf(stdout, "results:\n");
      objs = ProveRetrieve(&ts, NULL, obj, 0);
      ObjListPrettyPrint1(stdout, objs, 1, 0, 0, 0);
      ObjListPrettyPrint1(Log, objs, 1, 0, 0, 0);
      ObjListFree(objs);
    }
  }   
}   

/* Inference: Not currently in use. */

ObjList	*InferenceRules;
ObjList	*ActivationRules;

void InferenceInit()
{
  InferenceRules = NULL;
  ActivationRules = NULL;
  ProofRules = NULL;
}

Bool InferenceAdd(Obj *obj)
{
  Obj	*head;
  head = I(obj, 0);
  if (head == N("if")) {
    InferenceRules = ObjListCreate(obj, InferenceRules);
  } else if (head == N("if-activate")) {
    ActivationRules = ObjListCreate(obj, ActivationRules);
  } else if (head == N("ifthen")) {
    ProofRules = ObjListCreate(obj, ProofRules);
  } else return(0);
  return(1);
}

ObjList *InferenceRun1(Ts *ts, TsRange *tsr, Obj *obj, ObjList *inferences,
                       ObjList *more, Bool querydb)
{
  Obj		*obj1;
  ObjList	*p, *r;
  Proof		*proofs, *pr;
  Dbg(DBGOBJ, DBGHYPER, "InferenceRun1");
  r = NULL;
  for (p = inferences; p; p = p->next) {
    DbgOP(DBGOBJ, DBGHYPER, p->obj);
    if (Prove(ts, tsr, I(p->obj, 1), more, querydb, &proofs)) {
      for (pr = proofs; pr; pr = pr->next) {
        obj1 = ObjInstan(I(p->obj, 2), pr->bd);	/* todo: run a proof instead */
        ObjSetTsRange(obj1, ObjToTsRange(obj));
        DbgOP(DBGOBJ, DBGHYPER, obj1);
        r = ObjListCreate(obj1, r);
      }
      ProofFree(proofs);
    }
  }
  return(r);
}

ObjList *InferenceRunInferenceRules(Ts *ts, TsRange *tsr, Obj *obj,
                                    ObjList *more, Bool querydb)
{
  return(InferenceRun1(ts, tsr, obj, InferenceRules, more, querydb));
}

ObjList *InferenceRunActivationRules(Ts *ts, TsRange *tsr, Obj *obj,
                                     ObjList *more, Bool querydb)
{
  return(InferenceRun1(ts, tsr, obj, ActivationRules, more, querydb));
}

/* End of file. */
