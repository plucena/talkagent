/* semdisc.c */
void TsrGenAdviceClear(TsrGenAdvice *tga);
void TsrGenAdviceSet(TsrGenAdvice *tga);
void TsrGenAdviceGet(Obj *question, Discourse *dc, TsrGenAdvice *tga, Obj **current, int *gen_tsr);
void CompTenseHolderInit(CompTenseHolder *cth);
void GenAdviceInit(GenAdvice *ga);
Discourse *DiscourseCreate(char *langs, char *dialects);
void DiscourseFree(Discourse *dc);
void DiscourseContextInit(Discourse *dc, Ts *ts);
int DiscourseOpenChannel(Discourse *dc, int channeli, char *fn, int linelen, char *mode, int output_reps, int lang, int dialect, int style, int paraphrase_input, int echo_input, int batch_ok, char *s);
void DiscourseSetCurrentChannel(Discourse *dc, int channeli);
Channel *DiscourseGetInputChannel(Discourse *dc);
Dur DiscourseInputIdleTime(Discourse *dc);
Bool DiscourseWasActivity(Discourse *dc);
void DiscourseClearActivity(Discourse *dc);
Channel *DiscourseGetIthChannel(Discourse *dc, int channeli);
void DiscourseCloseChannel(Discourse *dc, int channeli);
void DiscourseCloseChannels(Discourse *dc);
void DiscoursePrintCurrentChannel(FILE *Log, Discourse *dc);
Bool DiscourseReadUnderstand(Discourse *dc);
void DiscourseSynParse(Channel *ch, Discourse *dc);
void DiscourseInit(Discourse *dc, Ts *ts, Obj *class, Obj *speaker, Obj *listener, int realtime);
void DiscourseParse(int translate, Obj *speaker, Obj *listener);
void DiscourseSetLang(Discourse *dc, int lang);
void DiscourseNextSentence(Discourse *dc);
void DiscourseEndOfSentence(Discourse *dc);
char *AgencyTypeToString(int rep_type);
void DiscourseObjRep(Discourse *dc, int rep_type, Obj *obj, int eoschar);
void DiscourseObjListRep(Discourse *dc, int rep_type, ObjList *objs);
void DiscoursePNodeRep(Discourse *dc, int rep_type, PNode *pn);
Channel *DiscourseSetCurrentChannelWrite(Discourse *dc, int i);
int DiscourseCurrentChannelLineLen(Discourse *dc);
Channel *DiscourseSetCurrentChannelRW(Discourse *dc, int i);
Channel *DiscourseSetCurrentChannelSameLangAsInput(Discourse *dc, int i);
void DiscourseGen(Discourse *dc, Bool is_input, char *preface, Obj *obj, int eos);
void DiscourseEchoInput2(Discourse *dc, Channel *ch, int i, char *s, int is_sent);
void DiscourseEchoInput1(Discourse *dc, char *s, int is_sent);
void DiscourseEchoInput(Discourse *dc);
void DiscourseEchoInputChannel(Discourse *dc, Channel *ch, int i);
void DiscourseGenParagraph(Discourse *dc, char *preface, ObjList *in_objs, int sort_by_ts, int gen_pairs);
void DiscoursePrintDeicticStack(FILE *stream, Discourse *dc);
Bool DiscourseDeicticStackLevelEmpty(Discourse *dc, int stack_level);
void DiscourseDeicticStackClear(Discourse *dc);
void DiscourseDeicticStackPush(Discourse *dc, Obj *class, ObjList *speakers, ObjList *listeners, Ts *now, int realtime);
void DiscourseDeicticStackSpeakersSet(Discourse *dc, ObjList *speakers);
void DiscourseDeicticStackPop(Discourse *dc);
void DiscourseSetDeicticStackLevel(Discourse *dc, int stack_level);
void DiscourseFlipListenerSpeaker(Discourse *dc);
void DiscourseAnaphorInit(Discourse *dc);
void DiscourseAnaphorAdd(Discourse *dc, Obj *con, Obj *source_con, PNode *pn, int gender, int number, int person);
void DiscourseAnaphorCommit(Discourse *dc, PNode *pn_top);
int DiscourseAddress(Discourse *dc);
Obj *DiscourseSpeaker(Discourse *dc);
Obj *DiscourseListener(Discourse *dc);
Bool DiscourseSpeakerIs(Discourse *dc, Obj *obj);
Bool DiscourseIsASpeaker(Discourse *dc, Obj *obj);
Bool DiscourseListenerIs(Discourse *dc, Obj *obj);
Bool DiscourseIsAListener(Discourse *dc, Obj *obj);
Bool DiscourseSerialComma(Discourse *dc);
Obj *DiscourseIsaArticle(Discourse *dc);
int DiscourseGenderOf(Obj *obj, Discourse *dc);
