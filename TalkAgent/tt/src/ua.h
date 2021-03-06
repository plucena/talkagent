/* ua.c */
void UA_Actor(Discourse *dc, Context *cx, Obj *in);
void UnderstandRunActorTsUA(Discourse *dc, Context *cx, Actor *ac, void (*fn)(Actor *, Ts *, Obj *, TsRange *), char *fn_name, TsRange *tsr_in, Float *numer, Float *denom);
void UnderstandRunActorUA(Discourse *dc, Context *cx, Actor *ac, void (*fn)(Actor *, Ts *, Obj *, Obj *), char *fn_name, Obj *in, Float *numer, Float *denom);
void UnderstandRunActorUAs1(Discourse *dc, Context *cx, Actor *ac, Obj *in, Float *numer, Float *denom);
void UnderstandRunActorUAs(Discourse *dc, Context *cx, Obj *in, Float *numer, Float *denom);
void UnderstandRunContextUA(Discourse *dc, Context *cx, void (*fn)(Context *, Ts *, Obj *), char *fn_name, Obj *in, Float *numer, Float *denom);
void UnderstandRunContextUAsPre(Discourse *dc, Context *cx, Obj *in, Float *numer, Float *denom);
void UnderstandRunContextUAsPost(Discourse *dc, Context *cx, Obj *in, Float *numer, Float *denom);
Float UA_Statement(Discourse *dc, Context *cx, Obj *obj, int eoschar);
Float UnderstandUtterance4(Discourse *dc, Context *cx, Obj *obj, int eoschar, int utype, Answer **an);
Float UnderstandUtterance3(Discourse *dc, Context *cx, Obj *obj, int eoschar, int utype, Answer **an);
Float UnderstandUtterance2(Discourse *dc, Context *cx, Obj *obj, int eoschar, int utype, Answer **an);
Float UnderstandUtterance1(Discourse *dc, Context *cx, Obj *in, int eoschar, int utype, Answer **an);
void UA_Infer(Discourse *dc, Context *cx, Ts *ts, Obj *in, Obj *justification);
Bool UA_CanOnlyBeQuestionConcept(Obj *obj);
void UnderstandAsQuestion(Discourse *dc, Obj *obj, PNode *pn, int eoschar, Anaphor *anaphors, Context *parent_cx, Context **children_cxs_p);
void UnderstandAsStatement(Discourse *dc, Obj *obj, PNode *pn, int eoschar, Anaphor *anaphors, Context *parent_cx, Context **children_cxs_p);
void UnderstandAlternative(Discourse *dc, ObjList *p, Obj *obj, ObjList *q, int eoschar, Context *parent_cx, Context **children_cxs_r);
void UnderstandAlternatives(Discourse *dc, ObjList *in_objs, int eoschar, Context **children_cxs_r);
void UnderstandSwitchAndPrune(Discourse *dc, Context *children_cxs, int eoschar);
void UnderstandCommitAnaphora(Discourse *dc);
void UnderstandCommentaryAdd(Context *cx);
void UnderstandAnswerQuestion(Discourse *dc);
ObjList *Understand_SpeechAct(ObjList *objs, Discourse *dc);
void Understand(Discourse *dc, ObjList *in_objs, int eoschar);
ObjList *UnderstandSentence2(Channel *ch, size_t lowerb, size_t upperb, int eoschar, Discourse *dc);
void UnderstandSentence1(Channel *ch, size_t lowerb, size_t upperb, int eoschar, Discourse *dc);
void UnderstandSentence(Channel *ch, size_t lowerb, size_t upperb, int eoschar, Discourse *dc);
