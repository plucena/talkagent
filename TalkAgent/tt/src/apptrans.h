/* apptrans.c */
void ArgumentsClear(Arguments *args);
void ArgumentsToThetaFilled(Arguments *args, int *theta_filled);
Bool ArgumentsAddSUBJ(PNode *subj, Arguments *args);
Bool ArgumentsAddOBJ(PNode *obj, Arguments *args);
Bool ArgumentsAddIOBJ(PNode *iobj, Arguments *args);
Bool ArgumentsAddADJP(PNode *adjp, Arguments *args);
Bool ArgumentsAddPreAdv(PNode *adv, Arguments *args);
Bool ArgumentsAddPostAdv(PNode *adv, Arguments *args);
Bool ArgumentsSatisfiesThetaCriterion(Arguments *args);
void ArgumentsFillCasei(ObjToLexEntry *ole_src, Arguments *args);
ObjToLexEntry *ObjToLexEntryFindLink(Obj *obj, LexEntry *le);
ObjToLexEntry *ObjToLexEntryTransGet2(Obj *obj, Obj *value, char *feat, int paruniv, int *theta_filled, Discourse *dc);
ObjToLexEntry *ObjToLexEntryTransGet1(Obj *obj, Obj *value, char *feat, int paruniv, int depth, int *theta_filled, Discourse *dc);
ObjToLexEntry *ObjToLexEntryTransGet(Obj *obj, int pos, int *theta_filled, int tgtlang, ObjToLexEntry *ole_src, Discourse *dc);
void FeatureDefault(int lang, int pos, int *tense, int *gender, int *number, int *person, int *degree);
void TranslateGetFeatures(PNode *pn, int tgtlang, int pos, int *tense, int *gender, int *number, int *person, int *degree);
Obj *TranslateCompoundTense(Obj *comptense, int srclang, int tgtlang);
ObjList *PNodeConceptsFor1(PNode *pn, Obj *con, ObjList *r);
ObjList *PNodeConceptsFor(PNode *pn, ObjList *concepts);
ObjList *TranslateGetAllcons(PNode *pn, LexEntry *le);
PNode *TranslateObjList(ObjList *objs, Discourse *dc);
Bool TranslateGetOles(int pos, Obj *con, LexEntry *src_le, int *theta_filled, int tgtlang, Discourse *dc, ObjToLexEntry **ole_src, ObjToLexEntry **ole_tgt);
void TranslateInit(void);
void TranslateBegin(Channel *ch);
void TranslateSpitUntranslated(Channel *ch, size_t lowerb, size_t upperb);
void Translate(PNode *src_pn, ObjList *concepts, Channel *ch, int srclang, int tgtlang, Discourse *dc);
void TranslationEnd(Channel *ch, int eoschar, int tgtlang, Discourse *dc);
PNode *Translate1(PNode *pn, PNode *pnp, Obj *max, PNode *agree_np, int srclang, int tgtlang, Discourse *dc);
PNode *TranslateWord(PNode *pn, PNode *pnp, Obj *max, PNode *agree_np, int srclang, int tgtlang, Discourse *dc);
PNode *TranslateConstituent(PNode *pn, PNode *pnp, Obj *max, PNode *agree_np, int srclang, int tgtlang, Discourse *dc);
void TranslatePunc(char *tgt, char *src, int srclang, int tgtlang);
PNode *TranslateAWord(PNode *pn, PNode *pnp, Obj *max, int pos, int tense, int gender, int number, int person, int degree, int srclang, int tgtlang, Discourse *dc);
PNode *TranslateWordInflected(PNode *pn, PNode *pnp, Obj *max, int pos, int srclang, int tgtlang, Discourse *dc);
PNode *TranslateWordUninflected(PNode *pn, PNode *pnp, Obj *max, int pos, int srclang, int tgtlang, Discourse *dc);
void TranslateGenderNumberPersonDef(PNode *pn, int tgtlang, int *gender, int *number, int *person);
PNode *TranslateWordAgreeNP(PNode *pn, PNode *pnp, Obj *max, int pos, PNode *agree_np, int srclang, int tgtlang, Discourse *dc);
PNode *TranslateAdjDetp_NP(PNode *pn, PNode *pnp, Obj *max, int pos, PNode *adjp, PNode *np, char *punc1, char *punc2, int srclang, int tgtlang, Discourse *dc);
PNode *TranslatePrep_NP(PNode *pn, PNode *pnp, Obj *max, PNode *prep, PNode *np, int srclang, int tgtlang, Discourse *dc);
PNode *TranslateGenitive(PNode *pn, PNode *pnp, Obj *max, PNode *pn_np, PNode *pn_of_np, int srclang, int tgtlang, Discourse *dc);
PNode *TranslateJuxtaposition(PNode *pn, PNode *pnp, Obj *max, PNode *agree_np, int srclang, int tgtlang, Discourse *dc);
PNode *TranslatePP(PNode *pn, PNode *pnp, Obj *max, int srclang, int tgtlang, Discourse *dc);
PNode *TranslateVP1(PNode *pn, PNode *pnp, Obj *mainverb, Obj *max, LexEntry *le_mainverb, int gender, int number, int person, PNode *adv1, PNode *adv2, PNode *adv3, Obj *comptense, int srclang, int tgtlang, PNode *r, Discourse *dc);
PNode *TranslateVP(PNode *pn, PNode *pnp, Obj *max, int srclang, int tgtlang, Discourse *dc);
PNode *TranslateNP(PNode *pn, PNode *pnp, Obj *max, int srclang, int tgtlang, Discourse *dc);
PNode *TranslateADJP(PNode *pn, PNode *pnp, Obj *max, PNode *agree_np, int srclang, int tgtlang, Discourse *dc);
PNode *TranslateS(PNode *pn, PNode *pnp, Obj *max, int srclang, int tgtlang, Discourse *dc);
PNode *TranslateNPVP(PNode *pn, PNode *pnp, Obj *max, int srclang, int tgtlang, Discourse *dc);
PNode *TranslateS_AscriptiveAdj(PNode *pn, PNode *pnp, Obj *max, Obj *mainverb, LexEntry *le_mainverb, Obj *comptense, PNode *subjnp_src, PNode *adjp_src, int *theta_filled, int srclang, int tgtlang, PNode *r, Discourse *dc);
PNode *TranslateVerbAdjArgument(PNode *pn, PNode *pnp, Obj *max, int theta_role_i, LexEntry *preple, Arguments *args, int srclang, int tgtlang, Discourse *dc);
PNode *PNodeTranslateArgRecurse(PNode *pn, PNode *pnp, Obj *max, PNode *pnxx, int constit, Arguments *args, ObjToLexEntry *ole_tgt, Obj *match_cas, int srclang, int tgtlang, Discourse *dc, int *success);
PNode *TranslateS_VerbPred(PNode *pn, PNode *pnp, Obj *max, Obj *mainverb, LexEntry *le_mainverb, Obj *comptense, Arguments *args, int *theta_filled, int srclang, int tgtlang, PNode *r, Discourse *dc);
PNode *TranslateADJP_PP1(PNode *pn, PNode *pnp, Obj *max, PNode *adjp_src, PNode *adjp_tgt, Arguments *args, int srclang, int tgtlang, PNode *r, Discourse *dc);
PNode *TranslateADJP_PP(PNode *pn, PNode *pnp, Obj *max, PNode *agree_np, int srclang, int tgtlang, Discourse *dc);
