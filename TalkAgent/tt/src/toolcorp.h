/* toolcorp.c */
Corpus *CorpusCreate(void);
Article *ArticleCreate(char *filename, size_t startpos, size_t stoppos, Ts *ts, ObjList *speakers, ObjList *listeners, char *text);
char *CorpusIndexWord1(Corpus *corpus, char *word0, long offset, Article *article);
void CorpusIndexWord(Corpus *corpus, char *word0, long offset, Article *article);
void CorpusIndexWords(Corpus *corpus, Article *article);
void CorpusIndexArticle(Corpus *corpus, Article *article);
void CorpusIndexPNodeList(Corpus *corpus, Channel *ch, Ts *ts);
void CorpusPNodeListAddArticle(Channel *ch, char *filename, char *base, char *start, char *end);
void CorpusIndexFileString(Corpus *corpus, Channel *ch, char *filename, char *s, Ts *ts);
Bool CorpusIndexFile(Corpus *corpus, char *fn, Ts *ts0);
void CorpusIndexDirectory(Corpus *corpus, char *dirfn);
void CorpusInit(void);
Corpus *LangToCorpus(int lang);
Bool CorpusFrenchGender2(char *s, CorpusWordList *cwl);
void CorpusFrenchGender1(CorpusWordList *cwl, long *m, long *f);
int CorpusFrenchGender(Corpus *corpus, char *word);
void CorpusPrintWordList1(char *buf, CorpusWordList *cwl, int halflinelen, int prelen, int wordlen, int longfmt);
void CorpusPrintWordList(FILE *stream, CorpusWordList *cwl, char *word, int linelen, int longfmt);
void CorpusPrintWord(FILE *stream, Corpus *corpus, char *word0, int linelen);
void Corpus_AdverbialFinder3(FILE *outstream, char *begin, char *end);
void Corpus_AdverbialFinder2(FILE *outstream, char *begin, char *end, int numword_thresh);
void Corpus_AdverbialFinder1(FILE *outstream, char *p, int numword_thresh);
Bool Corpus_AdverbialFinderFile(char *in_fn, char *out_fn);
Bool Corpus_AdverbialFinderDirectory(char *dirfn, char *out_fn);
