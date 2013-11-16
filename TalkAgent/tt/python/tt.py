#
# ThoughtTreasure commonsense platform
#
# Copyright 1999 Erik Thomas Mueller. All Rights Reserved.
# See <URL:../ttlegal.txt> for legal details.
# Bugs to <URL:mailto:bugs@signiform.com>.
#

import string

def assertionParse(s):
  return assertionParse1(s,0)[0]

def assertionParse1(s,i):
  l=len(s)
  if i<l and s[i]=='@':
    i=i+1
    start=i
    while i<l:
      if s[i]=='|': break
      i=i+1
    toks=string.split(s[start:i],':')
    i=i+1
    (r,i)=assertionParse2(s,i)
    return (['time-range-of',r,['TSRANGE']+toks],i)
  else:
    return assertionParse2(s,i)

def assertionParse2(s,i):
  l=len(s)
  if i<l and s[i]=='[':
    i=i+1
    elems=[]
    while i<l and s[i]!=']':
      (a,i)=assertionParse1(s,i)
      if a==None: break
      elems.append(a)
      if i<l and s[i]==' ': i=i+1
    return (elems,i+1)
  else:
    inqt=0
    tok=''
    while i<l:
      if inqt:
        tok=tok+s[i]
        if s[i]=='"': inqt=0
        i=i+1
      else:
        if s[i] in ' []': break
        tok=tok+s[i]
        if s[i]=='"': inqt=1
        i=i+1
    return (tok,i)

def featureGet(features, ft, default):
  for f in features:
    if f in ft: return f;
  return default

def featureCon(feat):
  return 'F'+str(ord(feat))

# tt.number('NUMBER:second:1800', 'second') -> 1800
def number(s, numberClass):
  x=split(s,':')
  if x[1]==numberClass: return float(x[2])
  return None

#def whiteToSpace(s):
#  r=''
#  for c in s:
#    if c in LE_WHITESPACE0: r=r+' '
#    else: r=r+c
#  return r

def whiteToSpace(s):
  r=''
  for c in s:
    if c not in LE_NONWHITE: r=r+' '
    else: r=r+c
  return r

F_NULL              = '?'
F_TODO              = '@'
F_ENTER             = '%'
F_SLANG             = 'a'
F_PREPOSED_ADJ      = 'b'
F_CONDITIONAL       = 'c'
F_PAST_PARTICIPLE   = 'd'
F_PRESENT_PARTICIPLE = 'e'
F_INFINITIVE        = 'f'
F_BRITISH           = 'g'
F_PEJORATIVE        = 'h'
F_IMPERFECT         = 'i'
F_BORROWING         = 'j'
F_DEFINITE_ART      = 'k'
F_ETRE              = 'l'
F_MASS_NOUN         = 'm'
F_OLD               = 'o'
F_PRESENT           = 'p'
F_INFREQUENT        = 'q'
F_PASSE_SIMPLE      = 's'
F_LITERARY          = 't'
F_FUTURE            = 'u'
F_PROVERB           = 'v'
F_CLAUSE2_ONLY      = 'w'
F_S_POS             = 'x'
F_FRENCH            = 'y'
F_ENGLISH           = 'z'
F_ADJECTIVE         = 'A'
F_ADVERB            = 'B'
F_NEUTER            = 'C'
F_DETERMINER        = 'D'
F_ADJP              = 'E'
F_FEMININE          = 'F'
F_INDICATIVE        = 'G'
F_PRONOUN           = 'H'
F_IMPERATIVE        = 'I'
F_SUBJUNCTIVE       = 'J'
F_CONJUNCTION       = 'K'
F_ADVP              = 'L'
F_MASCULINE         = 'M'
F_NOUN              = 'N'
F_SUBCAT_SUBJUNCTIVE = 'O'
F_PLURAL            = 'P'
F_QUESTION          = 'Q'
F_PREPOSITION       = 'R'
F_SINGULAR          = 'S'
F_INFORMAL          = 'T'
F_INTERJECTION      = 'U'
F_VERB              = 'V'
F_VP                = 'W'
F_NP                = 'X'
F_PP                = 'Y'
F_S                 = 'Z'
F_EXPLETIVE         = '0'
F_FIRST_PERSON      = '1'
F_SECOND_PERSON     = '2'
F_THIRD_PERSON      = '3'
F_POSITIVE          = '6'
F_COMPARATIVE       = '7'
F_SUPERLATIVE       = '8'
F_ELEMENT           = '9'
F_TRADEMARK         = '�'
F_MODAL             = '�'
F_AMERICAN          = '�'
F_CANADIAN          = '�'
F_EMPTY_ART         = '�'
F_OTHER_DIALECT     = '�'
F_COMMON_INFL       = '�'
F_FREQUENT          = '�'
F_NAME              = '�'
F_IRONIC            = '�'
F_ISM               = '�'
F_TUTOIEMENT        = '�'
F_VOUVOIEMENT       = '�'
F_NO_PROGRESSIVE    = '�'
F_EPITHETE_ADJ      = '�'
F_PREDICATIVE_ADJ   = '�'
F_INFL_CHECKED      = '�'
F_REALLY            = '�'
F_SUBCAT_INDICATIVE = '�'
F_SUBCAT_INFINITIVE = '�'
F_SUBCAT_PRESENT_PARTICIPLE = '�'
F_CONTRACTION       = '�'
F_ELISION           = '�'
F_PREVOWEL          = '�'
F_ASPIRE            = '�'
F_VOCALIC           = '�'
F_BECOME            = '�'
F_OBJ1              = '�'
F_OBJ2              = '�'
F_OBJ3              = '�'
F_SUBJ12            = '�'
F_SUBJ2             = '�'
F_SUBJ3             = '�'
F_IOBJ3             = '�'
F_IOBJ4             = '�'
F_DO_NOT_REORDER    = '�'
F_GEN_ONLY          = '�'
F_TRANS_ONLY        = '�'
F_NO_INFLECT        = '�'
F_MASC_FEM          = '�'
F_FUSED             = '�'
F_INVARIANT         = '�'
F_PREFIX            = '�'
F_SUFFIX            = '�'
F_OPTIONAL          = '_'
F_NO_BZ_Z           = '�'
F_NO_ZB_Z           = '�'
F_NO_BW_W           = '�'
F_NO_WB_W           = '�'
F_NO_BE_E           = '�'
F_COORDINATOR       = '�'

FT_LANG             = "yz"
FT_POS              = "NHVA9BDKRUx0��"
FT_INITIAL_SOUND    = "��"
FT_LE_MORE          = "Qj�"
FT_TENSE            = "fpisucde"
FT_GENDER           = "MFC"
FT_NUMBER           = "SP"
FT_PERSON           = "123"
FT_MOOD             = "GJI"
FT_DEGREE           = "678"
FT_ALTER            = "���"
FT_MODALITY         = "�"
FT_STYLE            = "tTa"
FT_DIALECT          = "�g��o"
FT_FREQ             = "q�"
FT_FREQ_DICT        = "�?q"
FT_CONNOTE          = "h�"
FT_ADDRESS          = "��"
FT_EPITH_PRED       = "��"
FT_GR_ADJ           = "b"+FT_EPITH_PRED
FT_GR_ADV_BLK       = "�����"
FT_GR_CONJ_BLK      = "w"
FT_GR_CONJ_ENB      = "�"
FT_ARTICLE          = "k�"
FT_GR_N             = "m"+FT_ARTICLE
FT_GR_V             = "l��"
FT_FILTER           = ("�"+FT_GR_ADJ+FT_GR_ADV_BLK+FT_GR_CONJ_BLK+
                       FT_GR_CONJ_ENB+FT_GR_N+FT_GR_V)
FT_GRAMMAR          = "�"+FT_FILTER
FT_PARUNIV          = "�"
FT_TASK_RESTRICT    = "��"
FT_SUBJLOC          = "���"
FT_OBJLOC           = "���"
FT_IOBJLOC          = "��"
FT_SUBCAT           = "O�ϱ"
FT_SUBCATALL        = FT_SUBJLOC+FT_OBJLOC+FT_IOBJLOC+FT_SUBCAT+"_�"
FT_INSTRUCT         = "��߶�"
FT_NULL             = "?"
FT_OTHER            = FT_NULL+"@%v�+_"
FT_LEXENTRY         = FT_GENDER+FT_POS+FT_LANG+FT_INITIAL_SOUND+FT_LE_MORE
FT_LEXENTRY_ALL     = FT_POS+FT_LANG+FT_INITIAL_SOUND+FT_LE_MORE
FT_LE_MINUS         = FT_LE_MORE
FT_ADJLEXENTRY      = FT_POS+FT_LANG+FT_INITIAL_SOUND+FT_LE_MORE
FT_INFL             = (FT_TENSE+FT_GENDER+FT_NUMBER+FT_PERSON+FT_MOOD+
                       FT_ALTER+FT_DEGREE+FT_MODALITY)
FT_INFL_FILE        = FT_INFL+FT_STYLE+FT_DIALECT+FT_FREQ
FT_USAGE            = (FT_STYLE+FT_DIALECT+FT_FREQ+FT_CONNOTE+FT_ADDRESS+
                       FT_GRAMMAR+FT_PARUNIV+FT_TASK_RESTRICT)
FT_ALL              = (FT_LEXENTRY_ALL+FT_INFL+FT_USAGE+FT_SUBCATALL+
                       FT_INSTRUCT+FT_OTHER)
FT_GENDERPLUS       = FT_GENDER+"�"
FT_LINK             = FT_USAGE
FT_INFL_LINK        = FT_INFL+FT_LINK
FT_CONSTIT          = "ELWXYZ"

TREE_LEVEL          = '='
TREE_LEVEL_CAPITAL  = '*'
TREE_LEVEL_CONCRETE = '*'
TREE_LEVEL_MULTI    = '-'
TREE_LEVEL_CONTRAST = '-'
TREE_ESCAPE         = '\\'
TREES_ESCAPE        = "\\"
TREE_SLOT_SEP       = '|'
TREES_SLOT_SEP      = "|"

LE_PHRASE_INFLECT   = '*'
LE_PHRASE_NO_INFLECT = '#'
LE_SEP              = '/'
LES_SEP             = "/"
LE_FEATURE_SEP      = '.'
LES_FEATURE_SEP     = "."
LE_WHITESPACE0      = "-\"',;:/()"
LE_WHITESPACE       = " "+LE_WHITESPACE0
LE_NONWHITESPACE    = "$%.@"
LE_NONWHITE         = LE_NONWHITESPACE+string.letters+string.digits

RANGE_DEFAULT_FROM  = 0.1
RANGE_DEFAULT_TO    = 1.0

FEATDICT={'%': 'add to book',
'+': 'required',
'0': 'expletive',
'1': 'first person',
'2': 'second person',
'3': 'third person',
'6': 'positive degree',
'7': 'comparative degree',
'8': 'superlative degree',
'9': 'element',
'?': 'empty feature',
'@': 'entry which needs work',
'_': 'optional',
'�': 'American English',
'a': 'slang',
'A': 'adjective',
'b': 'preposed adjective',
'B': 'adverb',
'�': 'Canadian',
'C': 'neuter',
'c': 'conditional',
'd': 'past participle',
'D': 'determiner',
'�': 'object at index 2',
'�': 'object at index 1',
'�': 'zero article',
'e': 'gerund',
'F': 'feminine',
'f': 'infinitive',
'g': 'British English',
'G': 'indicative',
'h': 'derogatory',
'H': 'pronoun',
'�': 'other regional dialect',
'I': 'imperative',
'i': 'simple past',
'J': 'subjunctive',
'j': 'borrowing',
'k': 'definite article',
'K': 'conjunction',
'l': '�tre',
'm': 'mass noun',
'M': 'masculine',
'N': 'noun',
'O': 'subjunctive',
'�': 'do not reorder',
'�': 'attributive',
'o': 'dated',
'P': 'plural',
'p': 'present tense',
'q': 'infrequent',
'Q': 'interrogative',
'R': 'preposition',
'S': 'singular',
's': 'pass� simple',
'T': 'informal',
't': 'literary/technical',
'�': 'subject at index 3',
'�': 'group subject',
'u': 'future',
'U': 'interjection',
'v': 'proverb',
'V': 'verb',
'w': 'clause 2 only',
'x': 'sentence',
'y': 'French',
'z': 'English',
'�': 'masculine and feminine',
'�': 'coordinator',
'�': 'prefix',
'�': 'trademark',
'�': 'BZZ block',
'�': 'present participle',
'�': 'tutoiement',
'�': 'modal',
'�': 'no inflection',
#'�': 'really',
'�': 'checked inflection',
'�': 'frequent',
'�': 'multilingual name',
'�': 'suffix',
'�': 'translation only',
'�': 'ZBZ block',
'�': 'elision',
'�': 'pre-vowel',
'�': 'BEE block',
'�': 'aspirated',
'�': 'vocalic',
'�': 'contraction',
'�': 'abstract',
'�': 'infinitive',
'�': 'progressive nontaker',
'�': 'vouvoiement',
'�': 'predicative',
'�': 'BWW block',
'�': 'invariant',
'�': 'object at index 3',
'�': 'common inflection',
'�': 'WBW block',
'�': 'humorous',
'�': 'indirect object at index 4',
'�': 'indirect object at index 3',
'�': 'indicative',
'�': 'subject at index 2',
'�': 'fused'}

TRPOS_NA=''
TRPOS_PRE_VERB='_V'
TRPOS_POST_VERB_PRE_OBJ='V_O'
TRPOS_POST_VERB_POST_OBJ='VO_'

# End of file.
