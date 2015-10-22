package hirondelle.date4j;

import java.lang.reflect.Array;

final class ModelUtil
{
  static final int HASH_SEED = 23;
  private static final int fODD_PRIME_NUMBER = 37;
  
  static boolean areEqual(char paramChar1, char paramChar2)
  {
    return paramChar1 == paramChar2;
  }
  
  static boolean areEqual(double paramDouble1, double paramDouble2)
  {
    return Double.doubleToLongBits(paramDouble1) == Double.doubleToLongBits(paramDouble2);
  }
  
  static boolean areEqual(float paramFloat1, float paramFloat2)
  {
    return Float.floatToIntBits(paramFloat1) == Float.floatToIntBits(paramFloat2);
  }
  
  static boolean areEqual(long paramLong1, long paramLong2)
  {
    return paramLong1 == paramLong2;
  }
  
  static boolean areEqual(Object paramObject1, Object paramObject2)
  {
    if ((isArray(paramObject1)) || (isArray(paramObject2))) {
      throw new IllegalArgumentException("This method does not currently support arrays.");
    }
    if (paramObject1 == null) {
      return paramObject2 == null;
    }
    return paramObject1.equals(paramObject2);
  }
  
  static boolean areEqual(boolean paramBoolean1, boolean paramBoolean2)
  {
    return paramBoolean1 == paramBoolean2;
  }
  
  static <T extends Comparable<T>> int comparePossiblyNull(T paramT1, T paramT2, NullsGo paramNullsGo)
  {
    int j = 0;
    if ((paramT1 != null) && (paramT2 != null))
    {
      j = paramT1.compareTo(paramT2);
      return j;
    }
    int i;
    if ((paramT1 == null) && (paramT2 == null)) {
      i = j;
    }
    for (;;)
    {
      j = i;
      if (NullsGo.LAST != paramNullsGo) {
        break;
      }
      return i * -1;
      if ((paramT1 == null) && (paramT2 != null))
      {
        i = -1;
      }
      else
      {
        i = j;
        if (paramT1 != null)
        {
          i = j;
          if (paramT2 == null) {
            i = 1;
          }
        }
      }
    }
  }
  
  static boolean equalsFor(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2)
  {
    if (paramArrayOfObject1.length != paramArrayOfObject2.length) {
      throw new IllegalArgumentException("Array lengths do not match. 'This' length is " + paramArrayOfObject1.length + ", while 'That' length is " + paramArrayOfObject2.length + ".");
    }
    int i = 0;
    while (i < paramArrayOfObject1.length)
    {
      if (!areEqual(paramArrayOfObject1[i], paramArrayOfObject2[i])) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  private static int firstTerm(int paramInt)
  {
    return paramInt * 37;
  }
  
  static int hash(int paramInt, char paramChar)
  {
    return firstTerm(paramInt) + paramChar;
  }
  
  static int hash(int paramInt, double paramDouble)
  {
    return hash(paramInt, Double.doubleToLongBits(paramDouble));
  }
  
  static int hash(int paramInt, float paramFloat)
  {
    return hash(paramInt, Float.floatToIntBits(paramFloat));
  }
  
  static int hash(int paramInt1, int paramInt2)
  {
    return firstTerm(paramInt1) + paramInt2;
  }
  
  static int hash(int paramInt, long paramLong)
  {
    return firstTerm(paramInt) + (int)(paramLong >>> 32 ^ paramLong);
  }
  
  static int hash(int paramInt, Object paramObject)
  {
    int j;
    if (paramObject == null)
    {
      j = hash(paramInt, 0);
      return j;
    }
    if (!isArray(paramObject)) {
      return hash(paramInt, paramObject.hashCode());
    }
    int k = Array.getLength(paramObject);
    int i = 0;
    for (;;)
    {
      j = paramInt;
      if (i >= k) {
        break;
      }
      paramInt = hash(paramInt, Array.get(paramObject, i));
      i += 1;
    }
  }
  
  static int hash(int paramInt, boolean paramBoolean)
  {
    int i = firstTerm(paramInt);
    if (paramBoolean) {}
    for (paramInt = 1;; paramInt = 0) {
      return paramInt + i;
    }
  }
  
  static final int hashCodeFor(Object... paramVarArgs)
  {
    int j = 23;
    int k = paramVarArgs.length;
    int i = 0;
    while (i < k)
    {
      j = hash(j, paramVarArgs[i]);
      i += 1;
    }
    return j;
  }
  
  private static boolean isArray(Object paramObject)
  {
    return (paramObject != null) && (paramObject.getClass().isArray());
  }
  
  static Boolean quickEquals(Object paramObject1, Object paramObject2)
  {
    Boolean localBoolean = null;
    if (paramObject1 == paramObject2) {
      localBoolean = Boolean.TRUE;
    }
    while (paramObject1.getClass().isInstance(paramObject2)) {
      return localBoolean;
    }
    return Boolean.FALSE;
  }
  
  static String toStringAvoidCyclicRefs(Object paramObject, Class paramClass, String paramString)
  {
    return ToStringUtil.getTextAvoidCyclicRefs(paramObject, paramClass, paramString);
  }
  
  static String toStringFor(Object paramObject)
  {
    return ToStringUtil.getText(paramObject);
  }
  
  static enum NullsGo
  {
    FIRST,  LAST;
    
    private NullsGo() {}
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\hirondelle\date4j\ModelUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */