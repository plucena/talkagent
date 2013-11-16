/*
 * TTLexEntryToObj
 *
 * Copyright 1998, 1999 Erik Thomas Mueller
 * See <URL:../ttlegal.txt> for legal details.
 * Bugs to <URL:mailto:bugs@signiform.com>.
 *
 * 19981109T074747: begun
 */

package com.signiform.tt;

/**
 * A link between a ThoughtTreasure lexical entry and a ThoughtTreasure
 * object. That is, a word-meaning association.
 *
 * @author Erik T. Mueller
 * @version 0.00022
 */

public class TTLexEntryToObj {

/**
 * A lexical entry (word or phrase).
 */
public TTLexEntry lexentry;

/**
 * An object name (meaning).
 */
public String objname;

/**
 * A String containing single-character ThoughtTreasure feature
 * codes, specifying features that apply to this particular
 * word-meaning association. The feature codes are defined in
 * the <code>TT</code> class.
 *
 * @see TT
 */
public String features;

public String toString()
{
  return lexentry+":"+
         objname+":"+
         features;
}

}

/* End of file. */
