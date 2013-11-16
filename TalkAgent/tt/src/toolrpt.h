/* toolrpt.c */
void ReportInit(void);
void ReportFauxAmiChain3(Text *text, char *source, char *source_feat, Obj *concept, int source_lang, int source_dialect, char *target, char *target_feat, int target_lang, int target_dialect, Discourse *dc);
void ReportFauxAmiChain2(int paruniv, Text *text, LexEntry *source_le, Obj *obj, int ignore_infrequent, int source_lang, int target_lang, HashTable *source_ht, int source_dialect, int target_dialect, int depth, LexEntry *depth1_source_le, LexEntry *depth1_target_le, Obj *depth1_obj, Discourse *dc);
void ReportFauxAmiChain1(Text *text, LexEntry *source_le, int ignore_infrequent, int source_lang, int target_lang, HashTable *source_ht, int source_dialect, int target_dialect, Discourse *dc);
void ReportFauxAmi(Text *text, Discourse *dc);
void ReportFeatureSheetHeader(Rpt *rpt, Discourse *dc);
void ReportFeatureSheetLine(Rpt *rpt, int feature, Discourse *dc);
void ReportFeature(Text *text, Discourse *dc);
void ReportAddPhraseDerive(char *deriv);
void ReportPhraseDerivationHeader(Rpt *rpt, Discourse *dc);
void ReportPhraseDerivationLine(char *deriv, long value);
void ReportPhraseDerivation(Text *text, Discourse *dc);
void ReportWordListAdd(StringArray *sa, char *match_features, int palindrome_only, char *word, char *word_features);
void ReportWordList(FILE *stream_regular, FILE *stream_inverse, Obj *class, char *features, int all_inflections, int palindrome_only, int phrases_ok);
void ReportAnagramInit(void);
Bool ReportAnagramIn(char *word, AnagramClass *ac);
void ReportAnagramTrain1(LexEntry *le);
void ReportAnagramTrain(void);
AnagramClass *ReportGetAnagrams(char *word);
void ReportAnagramsOf1(Text *text, AnagramClass *ac, char *except, Discourse *dc);
void ReportAnagramsOf(Text *text, char *word, Discourse *dc);
void ReportAnagrams1(char *canon, AnagramClass *ac);
void ReportAnagram(Text *text, Discourse *dc);
void Tool_Report(void);
