package hirondelle.date4j;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ToStringUtil
{
  private static String HIDDEN_PASSWORD_VALUE = "****";
  private static final String NEW_LINE;
  private static Pattern PASSWORD_PATTERN;
  private static final String fAVOID_CIRCULAR_REFERENCES = "[circular reference]";
  private static final String fCLONE = "clone";
  private static final String fGET = "get";
  private static final String fGET_CLASS = "getClass";
  private static final String fHASH_CODE = "hashCode";
  private static final String fINDENT = "";
  private static final Logger fLogger;
  private static final Object[] fNO_ARGS = new Object[0];
  private static final Class[] fNO_PARAMS = new Class[0];
  private static final String fTO_STRING = "toString";
  
  static
  {
    fLogger = Util.getLogger(ToStringUtil.class);
    NEW_LINE = System.getProperty("line.separator");
    PASSWORD_PATTERN = Pattern.compile("password", 2);
  }
  
  private static void addEndLine(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append("}");
    paramStringBuilder.append(NEW_LINE);
  }
  
  private static void addLineForGetXXXMethod(Object paramObject, Method paramMethod, StringBuilder paramStringBuilder, Class paramClass, String paramString)
  {
    paramStringBuilder.append("");
    paramStringBuilder.append(getMethodNameMinusGet(paramMethod));
    paramStringBuilder.append(": ");
    paramObject = getMethodReturnValue(paramObject, paramMethod);
    if ((paramObject != null) && (paramObject.getClass().isArray())) {
      paramStringBuilder.append(Util.getArrayAsString(paramObject));
    }
    for (;;)
    {
      paramStringBuilder.append(NEW_LINE);
      return;
      if (paramClass == null)
      {
        paramStringBuilder.append(paramObject);
      }
      else if (paramClass == paramObject.getClass())
      {
        paramMethod = getMethodFromName(paramClass, paramString);
        if (isContributingMethod(paramMethod, paramClass)) {
          paramStringBuilder.append(getMethodReturnValue(paramObject, paramMethod));
        } else {
          paramStringBuilder.append("[circular reference]");
        }
      }
    }
  }
  
  private static void addStartLine(Object paramObject, StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append(paramObject.getClass().getName());
    paramStringBuilder.append(" {");
    paramStringBuilder.append(NEW_LINE);
  }
  
  private static Object dontShowPasswords(Object paramObject, Method paramMethod)
  {
    if (PASSWORD_PATTERN.matcher(paramMethod.getName()).find()) {
      paramObject = HIDDEN_PASSWORD_VALUE;
    }
    return paramObject;
  }
  
  private static Method getMethodFromName(Class paramClass, String paramString)
  {
    try
    {
      Method localMethod = paramClass.getMethod(paramString, fNO_PARAMS);
      return localMethod;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      vomit(paramClass, paramString);
    }
    return null;
  }
  
  private static String getMethodNameMinusGet(Method paramMethod)
  {
    String str = paramMethod.getName();
    paramMethod = str;
    if (str.startsWith("get")) {
      paramMethod = str.substring("get".length());
    }
    return paramMethod;
  }
  
  private static Object getMethodReturnValue(Object paramObject, Method paramMethod)
  {
    localObject1 = null;
    try
    {
      Object localObject2 = paramMethod.invoke(paramObject, fNO_ARGS);
      paramObject = localObject2;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      for (;;)
      {
        vomit(paramObject, paramMethod);
        paramObject = localObject1;
      }
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      for (;;)
      {
        vomit(paramObject, paramMethod);
        paramObject = localObject1;
      }
    }
    return dontShowPasswords(paramObject, paramMethod);
  }
  
  static String getText(Object paramObject)
  {
    return getTextAvoidCyclicRefs(paramObject, null, null);
  }
  
  static String getTextAvoidCyclicRefs(Object paramObject, Class paramClass, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    addStartLine(paramObject, localStringBuilder);
    Method[] arrayOfMethod = paramObject.getClass().getDeclaredMethods();
    int j = arrayOfMethod.length;
    int i = 0;
    while (i < j)
    {
      Method localMethod = arrayOfMethod[i];
      if (isContributingMethod(localMethod, paramObject.getClass())) {
        addLineForGetXXXMethod(paramObject, localMethod, localStringBuilder, paramClass, paramString);
      }
      i += 1;
    }
    addEndLine(localStringBuilder);
    return localStringBuilder.toString();
  }
  
  private static boolean isContributingMethod(Method paramMethod, Class paramClass)
  {
    boolean bool = Modifier.isPublic(paramMethod.getModifiers());
    int i;
    int j;
    label31:
    int k;
    if (paramMethod.getParameterTypes().length == 0)
    {
      i = 1;
      if (paramMethod.getReturnType() == Void.TYPE) {
        break label123;
      }
      j = 1;
      if (paramMethod.getReturnType() != paramClass) {
        break label128;
      }
      k = 1;
      label42:
      if ((!paramMethod.getName().equals("clone")) && (!paramMethod.getName().equals("getClass")) && (!paramMethod.getName().equals("hashCode")) && (!paramMethod.getName().equals("toString"))) {
        break label134;
      }
    }
    label123:
    label128:
    label134:
    for (int m = 1;; m = 0)
    {
      if ((!bool) || (i == 0) || (j == 0) || (m != 0) || (k != 0)) {
        break label140;
      }
      return true;
      i = 0;
      break;
      j = 0;
      break label31;
      k = 0;
      break label42;
    }
    label140:
    return false;
  }
  
  public static void main(String... paramVarArgs)
  {
    paramVarArgs = new ArrayList();
    paramVarArgs.add("blah");
    paramVarArgs.add("blah");
    paramVarArgs.add("blah");
    System.out.println(getText(paramVarArgs));
    paramVarArgs = new StringTokenizer("This is the end.");
    System.out.println(getText(paramVarArgs));
    paramVarArgs = new Ping(null);
    Pong localPong = new Pong(null);
    paramVarArgs.setPong(localPong);
    localPong.setPing(paramVarArgs);
    System.out.println(paramVarArgs);
    System.out.println(localPong);
  }
  
  private static void vomit(Class paramClass, String paramString)
  {
    fLogger.severe("Reflection fails to get no-arg method named: " + Util.quote(paramString) + " for class: " + paramClass.getName());
  }
  
  private static void vomit(Object paramObject, Method paramMethod)
  {
    fLogger.severe("Cannot get return value using reflection. Class: " + paramObject.getClass().getName() + " Method: " + paramMethod.getName());
  }
  
  private static final class Ping
  {
    private ToStringUtil.Pong fPong;
    
    public Integer getId()
    {
      return new Integer(123);
    }
    
    public ToStringUtil.Pong getPong()
    {
      return this.fPong;
    }
    
    public String getUserPassword()
    {
      return "blah";
    }
    
    public void setPong(ToStringUtil.Pong paramPong)
    {
      this.fPong = paramPong;
    }
    
    public String toString()
    {
      return ToStringUtil.getText(this);
    }
  }
  
  private static final class Pong
  {
    private ToStringUtil.Ping fPing;
    
    public ToStringUtil.Ping getPing()
    {
      return this.fPing;
    }
    
    public void setPing(ToStringUtil.Ping paramPing)
    {
      this.fPing = paramPing;
    }
    
    public String toString()
    {
      return ToStringUtil.getTextAvoidCyclicRefs(this, ToStringUtil.Ping.class, "getId");
    }
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\hirondelle\date4j\ToStringUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */