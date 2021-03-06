// Proxy class generated by ozone's OPP ($Revision: 1.1 $).
// DO NOT EDIT!

package br.usp.talkagent.kb.ozone.db;

import org.ozoneDB.*;
import org.ozoneDB.core.ObjectID;
import org.ozoneDB.core.Lock;
import org.ozoneDB.core.ResultConverter;

import br.usp.talkagent.kb.objects.ozConcept;

/**
 * This class was automatically generated by ozone's OPP.
 * Do not instantiate or use this class directly.
 */
public final class ozCategoriesImpl_Proxy 
       extends OzoneProxy
       implements br.usp.talkagent.kb.ozone.db.ozCategories {

   static final long	serialVersionUID = 1L;

   public ozCategoriesImpl_Proxy() {
      super();
      }


   public ozCategoriesImpl_Proxy (ObjectID oid, OzoneInterface link) {
      super (oid, link);
      }


   public static br.usp.talkagent.kb.ozone.db.ozCategories createObject(OzoneInterface link) {
      try {
         /*
            if (link == null)
               throw new TransactionExc ("Thread has not yet joined a transaction.");
         */
         
         Object[]   args  = {};
         OzoneProxy proxy = link.createObject ("br.usp.talkagent.kb.ozone.ozCategoriesImpl", OzoneInterface.Public, null, "", args);
         
         return (br.usp.talkagent.kb.ozone.db.ozCategories) proxy;
      } catch (ExceptionInOzoneObjectException e) {
         Throwable ee = e.getCause();
         
         // Hope that the compiler is not so smart and detect these statements as unreachable if these exceptions are already matched above
         if (ee instanceof RuntimeException) {
             throw (RuntimeException) ee;
         }
         
         if (ee instanceof Error) {
             throw (Error) ee;
         }
         
         throw e; // Unhandled exception.
      } catch (RuntimeException e) {
         e.fillInStackTrace();
         throw new UnexpectedException(e.toString());
      }
   }


   public java.util.Vector getCategories() {
      try {
         Object target = link.fetch (this, Lock.LEVEL_READ);

         if (target!=null) {
            return (java.util.Vector) ResultConverter.substituteOzoneCompatibles(((br.usp.talkagent.kb.ozone.db.ozCategoriesImpl) target).getCategories());
         } else {
            Object[] args   = {};
            Object   result = link.invoke(this, 7, args, Lock.LEVEL_READ);
            return (java.util.Vector) result;
         }
      } catch (ExceptionInOzoneObjectException e) {
         Throwable ee = e.getCause();
         
         // Hope that the compiler is not so smart and detect these statements as unreachable if these exceptions are already matched above
         if (ee instanceof RuntimeException) {
             throw (RuntimeException) ee;
         }
         
         if (ee instanceof Error) {
             throw (Error) ee;
         }
         
         throw e; // Unhandled exception.
      } catch (RuntimeException e) {
         e.fillInStackTrace();
         throw e;
      } catch (Exception e) {
         e.fillInStackTrace();
         throw new UnexpectedException (e.toString());
      }
   }


   public void addCategory(br.usp.talkagent.kb.objects.ozConcept arg0) {
      try {
         Object target = link.fetch (this, Lock.LEVEL_READ);

         if (target!=null) {
            arg0 = (br.usp.talkagent.kb.objects.ozConcept) ResultConverter.substituteOzoneCompatibles(arg0);
            ((br.usp.talkagent.kb.ozone.db.ozCategoriesImpl) target).addCategory(arg0);
         } else {
            Object[] args   = {arg0};
            Object   result = link.invoke(this, 0, args, Lock.LEVEL_READ);
         }
      } catch (ExceptionInOzoneObjectException e) {
         Throwable ee = e.getCause();
         
         // Hope that the compiler is not so smart and detect these statements as unreachable if these exceptions are already matched above
         if (ee instanceof RuntimeException) {
             throw (RuntimeException) ee;
         }
         
         if (ee instanceof Error) {
             throw (Error) ee;
         }
         
         throw e; // Unhandled exception.
      } catch (RuntimeException e) {
         e.fillInStackTrace();
         throw e;
      } catch (Exception e) {
         e.fillInStackTrace();
         throw new UnexpectedException (e.toString());
      }
   }


   public int getNumberOfAssertions() {
      try {
         Object target = link.fetch (this, Lock.LEVEL_READ);

         if (target!=null) {
            return ((br.usp.talkagent.kb.ozone.db.ozCategoriesImpl) target).getNumberOfAssertions();
         } else {
            Object[] args   = {};
            Object   result = link.invoke(this, 9, args, Lock.LEVEL_READ);
            return ((Integer)result).intValue();
         }
      } catch (ExceptionInOzoneObjectException e) {
         Throwable ee = e.getCause();
         
         // Hope that the compiler is not so smart and detect these statements as unreachable if these exceptions are already matched above
         if (ee instanceof RuntimeException) {
             throw (RuntimeException) ee;
         }
         
         if (ee instanceof Error) {
             throw (Error) ee;
         }
         
         throw e; // Unhandled exception.
      } catch (RuntimeException e) {
         e.fillInStackTrace();
         throw e;
      } catch (Exception e) {
         e.fillInStackTrace();
         throw new UnexpectedException (e.toString());
      }
   }


   public int getNumberOfConcepts() {
      try {
         Object target = link.fetch (this, Lock.LEVEL_READ);

         if (target!=null) {
            return ((br.usp.talkagent.kb.ozone.db.ozCategoriesImpl) target).getNumberOfConcepts();
         } else {
            Object[] args   = {};
            Object   result = link.invoke(this, 10, args, Lock.LEVEL_READ);
            return ((Integer)result).intValue();
         }
      } catch (ExceptionInOzoneObjectException e) {
         Throwable ee = e.getCause();
         
         // Hope that the compiler is not so smart and detect these statements as unreachable if these exceptions are already matched above
         if (ee instanceof RuntimeException) {
             throw (RuntimeException) ee;
         }
         
         if (ee instanceof Error) {
             throw (Error) ee;
         }
         
         throw e; // Unhandled exception.
      } catch (RuntimeException e) {
         e.fillInStackTrace();
         throw e;
      } catch (Exception e) {
         e.fillInStackTrace();
         throw new UnexpectedException (e.toString());
      }
   }


   public void setNumberOfAssertions(int arg0) {
      try {
         Object target = link.fetch (this, Lock.LEVEL_READ);

         if (target!=null) {
            ((br.usp.talkagent.kb.ozone.db.ozCategoriesImpl) target).setNumberOfAssertions(arg0);
         } else {
            Object[] args   = {new Integer(arg0)};
            Object   result = link.invoke(this, 21, args, Lock.LEVEL_READ);
         }
      } catch (ExceptionInOzoneObjectException e) {
         Throwable ee = e.getCause();
         
         // Hope that the compiler is not so smart and detect these statements as unreachable if these exceptions are already matched above
         if (ee instanceof RuntimeException) {
             throw (RuntimeException) ee;
         }
         
         if (ee instanceof Error) {
             throw (Error) ee;
         }
         
         throw e; // Unhandled exception.
      } catch (RuntimeException e) {
         e.fillInStackTrace();
         throw e;
      } catch (Exception e) {
         e.fillInStackTrace();
         throw new UnexpectedException (e.toString());
      }
   }


   public void setNumberOfConcepts(int arg0) {
      try {
         Object target = link.fetch (this, Lock.LEVEL_READ);

         if (target!=null) {
            ((br.usp.talkagent.kb.ozone.db.ozCategoriesImpl) target).setNumberOfConcepts(arg0);
         } else {
            Object[] args   = {new Integer(arg0)};
            Object   result = link.invoke(this, 22, args, Lock.LEVEL_READ);
         }
      } catch (ExceptionInOzoneObjectException e) {
         Throwable ee = e.getCause();
         
         // Hope that the compiler is not so smart and detect these statements as unreachable if these exceptions are already matched above
         if (ee instanceof RuntimeException) {
             throw (RuntimeException) ee;
         }
         
         if (ee instanceof Error) {
             throw (Error) ee;
         }
         
         throw e; // Unhandled exception.
      } catch (RuntimeException e) {
         e.fillInStackTrace();
         throw e;
      } catch (Exception e) {
         e.fillInStackTrace();
         throw new UnexpectedException (e.toString());
      }
   }


   public int makeOID() {
      try {
         Object target = link.fetch (this, Lock.LEVEL_READ);

         if (target!=null) {
            return ((br.usp.talkagent.kb.ozone.db.ozCategoriesImpl) target).makeOID();
         } else {
            Object[] args   = {};
            Object   result = link.invoke(this, 14, args, Lock.LEVEL_READ);
            return ((Integer)result).intValue();
         }
      } catch (ExceptionInOzoneObjectException e) {
         Throwable ee = e.getCause();
         
         // Hope that the compiler is not so smart and detect these statements as unreachable if these exceptions are already matched above
         if (ee instanceof RuntimeException) {
             throw (RuntimeException) ee;
         }
         
         if (ee instanceof Error) {
             throw (Error) ee;
         }
         
         throw e; // Unhandled exception.
      } catch (RuntimeException e) {
         e.fillInStackTrace();
         throw e;
      } catch (Exception e) {
         e.fillInStackTrace();
         throw new UnexpectedException (e.toString());
      }
   }


   public void adjustCount() {
      try {
         Object target = link.fetch (this, Lock.LEVEL_READ);

         if (target!=null) {
            ((br.usp.talkagent.kb.ozone.db.ozCategoriesImpl) target).adjustCount();
         } else {
            Object[] args   = {};
            Object   result = link.invoke(this, 2, args, Lock.LEVEL_READ);
         }
      } catch (ExceptionInOzoneObjectException e) {
         Throwable ee = e.getCause();
         
         // Hope that the compiler is not so smart and detect these statements as unreachable if these exceptions are already matched above
         if (ee instanceof RuntimeException) {
             throw (RuntimeException) ee;
         }
         
         if (ee instanceof Error) {
             throw (Error) ee;
         }
         
         throw e; // Unhandled exception.
      } catch (RuntimeException e) {
         e.fillInStackTrace();
         throw e;
      } catch (Exception e) {
         e.fillInStackTrace();
         throw new UnexpectedException (e.toString());
      }
   }


   public void adjustAssertions(int arg0) {
      try {
         Object target = link.fetch (this, Lock.LEVEL_READ);

         if (target!=null) {
            ((br.usp.talkagent.kb.ozone.db.ozCategoriesImpl) target).adjustAssertions(arg0);
         } else {
            Object[] args   = {new Integer(arg0)};
            Object   result = link.invoke(this, 1, args, Lock.LEVEL_READ);
         }
      } catch (ExceptionInOzoneObjectException e) {
         Throwable ee = e.getCause();
         
         // Hope that the compiler is not so smart and detect these statements as unreachable if these exceptions are already matched above
         if (ee instanceof RuntimeException) {
             throw (RuntimeException) ee;
         }
         
         if (ee instanceof Error) {
             throw (Error) ee;
         }
         
         throw e; // Unhandled exception.
      } catch (RuntimeException e) {
         e.fillInStackTrace();
         throw e;
      } catch (Exception e) {
         e.fillInStackTrace();
         throw new UnexpectedException (e.toString());
      }
   }


   public org.ozoneDB.core.ObjectID getObjectID() {
      try {
         Object target = link.fetch (this, Lock.LEVEL_READ);

         if (target!=null) {
            return (org.ozoneDB.core.ObjectID) ResultConverter.substituteOzoneCompatibles(((br.usp.talkagent.kb.ozone.db.ozCategoriesImpl) target).getObjectID());
         } else {
            Object[] args   = {};
            Object   result = link.invoke(this, 11, args, Lock.LEVEL_READ);
            return (org.ozoneDB.core.ObjectID) result;
         }
      } catch (ExceptionInOzoneObjectException e) {
         Throwable ee = e.getCause();
         
         // Hope that the compiler is not so smart and detect these statements as unreachable if these exceptions are already matched above
         if (ee instanceof RuntimeException) {
             throw (RuntimeException) ee;
         }
         
         if (ee instanceof Error) {
             throw (Error) ee;
         }
         
         throw e; // Unhandled exception.
      } catch (RuntimeException e) {
         e.fillInStackTrace();
         throw e;
      } catch (Exception e) {
         e.fillInStackTrace();
         throw new UnexpectedException (e.toString());
      }
   }
   }
