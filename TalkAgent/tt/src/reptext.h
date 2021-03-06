/* reptext.c */
Text *TextCreate(long sizeguess, int linelen, Discourse *dc);
Text *TextCreat(Discourse *dc);
void TextFree(Text *text);
void TextPutc(int c, Text *text);
void TextTabTo(int pos, Text *text);
void TextNewlineIfNecessary(Text *text);
void TextNextParagraph(Text *text);
void TextPuts(char *s, Text *text);
void TextPutsIndented(char *s, Text *text, char *indent_string);
void TextAttachNextWord(Text *text);
void TextPutword(char *s, int post, Text *text);
void TextPrintf(Text *text, char *fmt, ...);
void TextPrintfWord(Text *text, int post, char *fmt, ...);
void TextPrintSpaces(Text *text, int spaces);
void TextPrintFeatAbbrev(Text *text, int feature, int force, int post, Discourse *dc);
void TextPrintConceptAbbrev(Text *text, Obj *con, int force, int post, Discourse *dc);
void TextPrintFeaturesAbbrev(Text *text, char *features, int force, char *except, Discourse *dc);
void TextPrintWordAndFeatures(Text *text, int html, int lang, char *word, char *features, ThetaRole *theta_roles, int force, int post, char *except, Discourse *dc);
void TextPrintTTObject(Text *text, Obj *obj, Discourse *dc);
void TextPrintConcept(Text *text, Obj *obj, int pos, int paruniv, int gender, int number, int person, int post, int toupper, int ancestor_ok, int showgender, Discourse *dc);
void TextPrintPhrase(Text *text, int post, char *phrase);
void TextPrintIsa(Text *text, Obj *obj, Obj *parent, Discourse *dc);
int TextLastChar(Text *text);
void TextFlush(Text *text);
void TextPrint1(FILE *stream, Text *text);
void TextPrint(FILE *stream, Text *text);
void TextPrintWithPreface1(FILE *stream, char *preface, Text *text);
void TextPrintWithPreface(FILE *stream, char *preface, Text *text);
void TextToString(Text *text, int maxlen, char *s);
char *TextString(Text *text);
void TextPutPNode(Text *text, PNode *pn, int eos, Discourse *dc);
void TextPutPNodes(Text *text, ObjList *pns, Discourse *dc);
void TextGen(Text *text, Obj *obj, int eos, Discourse *dc);
void TextGenParagraph(Text *text, ObjList *in_objs, int sort_by_ts, int gen_pairs, Discourse *dc);
