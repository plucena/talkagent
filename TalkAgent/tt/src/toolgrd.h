/* toolgrd.c */
void GrinderGrind(void);
void GrinderPreambleWrite(FILE *stream);
void GrinderGrindObjs(char *fn);
void GrinderAssertionWrite2(FILE *stream, Obj *pred, Obj *arg1, Obj *arg2);
void GrinderAssertionWrite(FILE *stream, Obj *obj);
void GrinderFeatureWrite(FILE *stream, char *s);
void GrinderWordWrite(FILE *stream, char *s);
void GrinderLeWriteUid(FILE *stream, LexEntry *le);
void GrinderPhraseSepWrite(FILE *stream, char *s);
void GrinderOleWrite(FILE *stream, ObjToLexEntry *ole);
void GriderGrindObj(FILE *stream, Obj *obj);
void GrinderGrindLes(char *fn);
void GrinderThetaRoleWrite(FILE *stream, ThetaRole *tr, int slotnum);
void GrinderThetaRolesWrite(FILE *stream, ThetaRole *theta_roles);
void GrinderLeoWrite(FILE *stream, LexEntryToObj *leo);
void GrinderGrindLe(FILE *stream, LexEntry *le);
void GrinderGrindWord(FILE *stream, Word *w, LexEntry *le);
void GrinderGrindWords(FILE *stream, Word *words, LexEntry *le);
void GrinderGrindInfls(char *fn);
