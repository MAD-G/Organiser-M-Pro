package hirondelle.date4j;

import java.lang.reflect.Array;
import java.util.logging.Logger;

final class Util
{
  private static final String SINGLE_QUOTE = "'";
  
  private static void checkObjectIsArray(Object paramObject)
  {
    if (!paramObject.getClass().isArray()) {
      throw new IllegalArgumentException("Object is not an array.");
    }
  }
  
  static String getArrayAsString(Object paramObject)
  {
    if (paramObject == null) {
      return "null";
    }
    checkObjectIsArray(paramObject);
    StringBuilder localStringBuilder = new StringBuilder("[");
    int j = Array.getLength(paramObject);
    int i = 0;
    if (i < j)
    {
      Object localObject = Array.get(paramObject, i);
      if (isNonNullArray(localObject)) {
        localStringBuilder.append(getArrayAsString(localObject));
      }
      for (;;)
      {
        if (!isLastItem(i, j)) {
          localStringBuilder.append(", ");
        }
        i += 1;
        break;
        localStringBuilder.append(localObject);
      }
    }
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
  
  static Logger getLogger(Class<?> paramClass)
  {
    return Logger.getLogger(paramClass.getPackage().getName());
  }
  
  private static boolean isLastItem(int paramInt1, int paramInt2)
  {
    return paramInt1 == paramInt2 - 1;
  }
  
  private static boolean isNonNullArray(Object paramObject)
  {
    return (paramObject != null) && (paramObject.getClass().isArray());
  }
  
  static String quote(Object paramObject)
  {
    return "'" + String.valueOf(paramObject) + "'";
  }
  
  static boolean textHasContent(String paramString)
  {
    return (paramString != null) && (paramString.trim().length() > 0);
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\hirondelle\date4j\Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */