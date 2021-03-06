/* toolsh.c */
void Tool_Shell_Std(void);
void Tool_Shell_Prompt(FILE *in, FILE *out);
void Tool_Shell(FILE *in, FILE *out, FILE *err, Discourse *dc);
int Tool_Shell_Error(char *errmsg, char *arg0, char *opt, char *line, FILE *in, FILE *out, FILE *err, Discourse *dc);
void ShellArgsInit(ShellArgs *sa);
int Tool_Shell_Interpret(char *line, FILE *in, FILE *out, FILE *err, Discourse *dc);
int Tool_Shell_Interpret_Command(char *cmd, ShellArgs *sa, char *line, FILE *in, FILE *out, FILE *err, Discourse *dc);
void Legal(void);
void Help(void);
int Tool_Shell_Interpret_Other_Command(char *buf, ShellArgs *sa, FILE *in, FILE *out, FILE *err, Discourse *dc);
int Tool_Shell_Parse(int translate, Obj *speaker, Obj *listener, char *line, FILE *in, FILE *out, FILE *err, Discourse *dc);
int Tool_Shell_ParseCompoundNoun(Discourse *dc);
int Tool_Shell_ObjHref(Obj *obj, FILE *in, FILE *out, FILE *err, Discourse *dc);
int System(char *cmd);
int Tool_Shell_Validate(char *new, char *old, char *line, FILE *in, FILE *out, FILE *err, Discourse *dc);
int Tool_Shell_TT(char *file, char *line, FILE *in, FILE *out, FILE *err, Discourse *dc);
int Tool_Shell_Dbg(int flags, int level, char *line, FILE *in, FILE *out, FILE *err, Discourse *dc);
int Tool_Shell_CorpusLoad(int lang, char *file, char *dir, char *line, FILE *in, FILE *out, FILE *err, Discourse *dc);
int Tool_Shell_CorpusFind(int lang, char *word, char *line, FILE *in, FILE *out, FILE *err, Discourse *dc);
