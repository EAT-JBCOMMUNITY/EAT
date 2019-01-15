/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.dependencytreeparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * @author panos
 */
public class DependencyTreeMethods {

    public static HashMap<String, String> jarClassPaths = new HashMap<>();
    public static HashSet<Artifact> artifacts = new HashSet<>();
    public static HashSet<String> testsuiteArtifactsPaths = new HashSet<>();
    public static HashSet<String> unloadedClasses = new HashSet<>();

    public static void printDependencies() throws IOException {
        String filePath = System.getProperty("DependencyTreeFilePath");
        boolean deleteAll = true;
        String keyWord = "--- maven-dependency-plugin";
        String keyWord2 = "--------------------------------------------------";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                if (line.trim().isEmpty() || line.contains(keyWord2)) {
                    deleteAll = true;
                }

                if (!deleteAll) {
                    if (line.contains("+") || line.contains("\\")) {
                        sb.append(line.replaceAll("\\+", "").replaceFirst("-", "").replaceAll("\\|", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("INFO", "").replace("\\", "").trim());
                    } else {
                        sb.append(line.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("INFO", "").trim());
                    }
                    sb.append(System.lineSeparator());
                }

                line = br.readLine();

                if (line != null && line.contains(keyWord)) {
                    deleteAll = false;
                    line = br.readLine();
                }

            }
            String everything = sb.toString();
            System.out.println(everything);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
    }

    public static HashSet<Artifact> getArtifacts() throws IOException {
        String filePath = System.getProperty("DependencyTreeFilePath");
        boolean deleteAll = true;
        String keyWord = "--- maven-dependency-plugin";
        String keyWord2 = "--------------------------------------------------";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                if (line.trim().isEmpty() || line.contains(keyWord2)) {
                    deleteAll = true;
                }

                if (!deleteAll) {
                    Artifact art = new Artifact();
                    String[] parts;
                    if (line.contains("+") || line.contains("\\")) {
                        parts = line.replaceAll("\\+", "").replaceFirst("-", "").replaceAll("\\|", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("INFO", "").replace("\\", "").trim().split(":");
                    } else {
                        parts = line.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("INFO", "").trim().split(":");
                    }

                    if (parts.length == 5) {
                        art.artifactId = parts[0];
                        art.groupId = parts[1];
                        art.type = parts[2];
                        art.version = parts[3];
                        art.scope = parts[4];

                        artifacts.add(art);
                    } else if (parts.length == 6) {
                        art.artifactId = parts[0];
                        art.groupId = parts[1];
                        art.type = parts[2];
                        art.version = parts[4];
                        art.scope = parts[5];

                        artifacts.add(art);
                    }

                }

                line = br.readLine();

                if (line != null && line.contains(keyWord)) {
                    deleteAll = false;
                    line = br.readLine();
                }

            }

            String filePath2 = System.getProperty("ExternalDependencyPath");
            artifacts = addExternalLibraries(artifacts, filePath2);

            //    for(Artifact a:artifacts){
            //        System.out.println(a.artifactId + " " + a.groupId + " " + a.version + " " + a.type + " " + a.scope);
            //    }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            br.close();
            return artifacts;
        }
    }

    public static HashSet<String> addExcludedLibraries() throws IOException {
        HashSet<String> excludedLibraries = new HashSet<>();
        String filePath = System.getProperty("ExcludedDependenciesPath");

        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(filePath);
            if (fr != null) {
                br = new BufferedReader(fr);
                String line = br.readLine();

                while (line != null) {
                    excludedLibraries.add(line);
                    line = br.readLine();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
            return excludedLibraries;
        }
    }

    public static HashSet<Artifact> addExternalLibraries(HashSet<Artifact> artifacts, String filePath) throws IOException {
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(filePath);
            if (fr != null) {
                br = new BufferedReader(fr);
                String line = br.readLine();

                while (line != null) {
                    Artifact art = new Artifact();
                    String[] parts;
                    parts = line.trim().split(":");

                    if (parts.length == 4) {
                        art.artifactId = parts[0];
                        art.groupId = parts[1];
                        art.type = parts[2];
                        art.version = parts[3];

                        artifacts.add(art);
                    }

                    line = br.readLine();
                }

                //    for(Artifact a:artifacts){
                //        System.out.println(a.artifactId + " " + a.groupId + " " + a.version + " " + a.type + " " + a.scope);
                //    }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
            return artifacts;
        }
    }

    public static HashMap<String, String> listJarClassPaths() {
        HashMap<String, String> jarClassPaths = new HashMap<>();

        try {
            String repoPath = System.getProperty("MavenRepoPath");

            for (Artifact ar : artifacts) {
                if (ar.type.contains("jar")) {
                    //    System.out.println(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar");
                    jarClassPaths.putAll(DependencyTreeMethods.listJarClassPaths(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//") + "/" + ar.groupId + "/" + ar.version + "/" + ar.groupId + "-" + ar.version + ".jar"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return jarClassPaths;
        }
    }

    public static HashMap<String, String> listFieldsOfJarClass(String path, String className) {
        HashMap<String, String> jarClassFields = new HashMap<>();

        try {
            if (path != null) {
                JarFile jarFile = new JarFile(path);

                URL[] urls = {new URL("jar:file:" + path + "!/")};
                URLClassLoader cl = URLClassLoader.newInstance(urls);

                Enumeration<JarEntry> allEntries = jarFile.entries();
                while (allEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) allEntries.nextElement();
                    String name = entry.getName();

                    //        System.out.println("Entry Name : " + name);
                    if (name.replaceAll("/", ".").replaceAll("-", ".").contains(className) && name.endsWith(".class") && !entry.isDirectory()) {
                        name = name.substring(0, name.lastIndexOf(".class"));
                        name = name.replaceAll("/", ".");
                        name = name.replaceAll("-", ".");

                        try {
                            Class clas = cl.loadClass(name);

                            for (Class<?> c = clas; c != null; c = c.getSuperclass()) {
                                for (Field f : c.getFields()) {
                                    if (!Modifier.toString(f.getModifiers()).contains("private")) {
                                        if (!jarClassFields.keySet().contains(f)) {
                                            jarClassFields.put(f.getName(), f.getType().toString());
                                        }
                                    }
                                }
                            }

                        } catch (Exception ex) {
                            //    ex.printStackTrace();
                        }
                    }

                }
                jarFile.close();
            }
        } catch (Exception e) {
            //    e.printStackTrace();
            System.out.println(path + " is not available.");
        } finally {
            return jarClassFields;
        }
    }

    public static HashMap<String, ArrayList<Class[]>> listClasses() {
        HashMap<String, ArrayList<Class[]>> jarClasses = new HashMap<>();

        try {
            String repoPath = System.getProperty("MavenRepoPath");

            for (Artifact ar : artifacts) {
                if (ar.type.contains("jar")) {
                    //    System.out.println(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar");
                    jarClasses.putAll(DependencyTreeMethods.listJarClasses(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//") + "/" + ar.groupId + "/" + ar.version + "/" + ar.groupId + "-" + ar.version + ".jar"));
                    jarClassPaths.putAll(listJarClassPaths(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//") + "/" + ar.groupId + "/" + ar.version + "/" + ar.groupId + "-" + ar.version + ".jar"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return jarClasses;
        }
    }

    public static HashMap<String, HashMap<String, String[]>> listMethods() {
        HashMap<String, HashMap<String, String[]>> classMethods = new HashMap<>();

        try {
            String repoPath = System.getProperty("MavenRepoPath");
/*
            unloadedClasses.add("LaxMaxAgeHandler");
            unloadedClasses.add("ThreadSafeClientConnManager$1");
            unloadedClasses.add("ThreadSafeClientConnManager");
            unloadedClasses.add("DefaultRedirectStrategy");
            unloadedClasses.add("NTLMEngineImpl$Type1Message");
            unloadedClasses.add("ClientExecChain");
            unloadedClasses.add("SSLSocketFactory");
            unloadedClasses.add("CookieSpecParamBean");
            unloadedClasses.add("ConnectionPoolTimeoutException");
            unloadedClasses.add("AuthParams");
            unloadedClasses.add("LayeredSocketFactory");
            unloadedClasses.add("ClientConnectionOperator");
            unloadedClasses.add("SchemePortResolver");
            unloadedClasses.add("DefaultSchemePortResolver");
            unloadedClasses.add("ConnManagerParams");
            unloadedClasses.add("PoolingHttpClientConnectionManager$1");
            unloadedClasses.add("ConnManagerParamBean");
            unloadedClasses.add("ConnectionHolder");
            unloadedClasses.add("CookieSpecBase");
            unloadedClasses.add("BasicAuthCache");
            unloadedClasses.add("HttpConnPool");
            unloadedClasses.add("BestMatchSpec");
            unloadedClasses.add("RedirectException");
            unloadedClasses.add("AutoRetryHttpClient");
            unloadedClasses.add("TLMEngineImpl$Type2Message");
            unloadedClasses.add("NTLMEngineImpl$Type2Message");
            unloadedClasses.add("KerberosScheme");
            unloadedClasses.add("HttpClientUtils");
            unloadedClasses.add("AbstractHttpClient");
            unloadedClasses.add("OperatedClientConnection");
            unloadedClasses.add("PoolingHttpClientConnectionManager$ConfigData");
            unloadedClasses.add("NTLMEngineException");
            unloadedClasses.add("DefaultClientConnectionReuseStrategy");
            unloadedClasses.add("RequestAuthenticationBase");
            unloadedClasses.add("IgnoreSpec");
            unloadedClasses.add("InternalHttpClient$1");
            unloadedClasses.add("DefaultUserTokenHandler");
            unloadedClasses.add("SocketFactoryAdaptor");
            unloadedClasses.add("RFC6265LaxSpec");
            unloadedClasses.add("CPool");
            unloadedClasses.add("RFC6265CookieSpec");
            unloadedClasses.add("RFC2109SpecProvider");
            unloadedClasses.add("GzipCompressingEntity");
            unloadedClasses.add("PublicSuffixDomainFilter");
            unloadedClasses.add("SchemeLayeredSocketFactory");
            unloadedClasses.add("NetscapeDraftSpec");
            unloadedClasses.add("ConnRouteParams");
            unloadedClasses.add("HttpClientBuilder");
            unloadedClasses.add("AbstractPoolEntry");
            unloadedClasses.add("DefaultCookieSpec");
            unloadedClasses.add("CPoolEntry");
            unloadedClasses.add("CPoolProxy");
            unloadedClasses.add("HttpUriRequest");
            unloadedClasses.add("CookieAttributeHandler");
            unloadedClasses.add("ClientConnectionManager");
            unloadedClasses.add("AbstractExecutionAwareRequest$1");
            unloadedClasses.add("PoolingClientConnectionManager$1");
            unloadedClasses.add("KerberosSchemeFactory");
            unloadedClasses.add("SPNegoScheme");
            unloadedClasses.add("BasicMaxAgeHandler");
            unloadedClasses.add("BrowserCompatVersionAttributeHandler");
            unloadedClasses.add("CookieSpecProvider");
            unloadedClasses.add("SingleClientConnManager$1");
            unloadedClasses.add("InvalidCredentialsException");
            unloadedClasses.add("BrowserCompatSpec");
            unloadedClasses.add("SocketFactory");
            unloadedClasses.add("StandardHttpRequestRetryHandler");
            unloadedClasses.add("IdleConnectionHandler");
            unloadedClasses.add("BasicExpiresHandler");
            unloadedClasses.add("AuthCache");
            unloadedClasses.add("ManagedHttpClientConnection");
            unloadedClasses.add("TargetAuthenticationStrategy");
            unloadedClasses.add("MultipartFormEntity");
            unloadedClasses.add("HttpRoutedConnection");
            unloadedClasses.add("BasicClientConnectionManager$1");
            unloadedClasses.add("NonRepeatableRequestException");
            unloadedClasses.add("DigestScheme");
            unloadedClasses.add("HttpNIOConnPool");
            unloadedClasses.add("StringBody");
            unloadedClasses.add("MultipartEntityBuilder");
            unloadedClasses.add("RequestEntityProxy");
            unloadedClasses.add("SchemeRegistry");
            unloadedClasses.add("InputStreamBody");
            unloadedClasses.add("AsyncByteConsumer");
            unloadedClasses.add("DefaultHttpRequestParser");
            unloadedClasses.add("ByteArrayBody");
            unloadedClasses.add("MinimalClientExchangeHandlerImpl");
            unloadedClasses.add("MalformedCookieException");
            unloadedClasses.add("RFC6265CookieSpecProvider$1");
            unloadedClasses.add("MultipartEntity");
            unloadedClasses.add("CloseableHttpAsyncClientBase$1");
            unloadedClasses.add("EntityEnclosingRequestWrapper");
            unloadedClasses.add("AbstractContentBody");
            unloadedClasses.add("LoggingIOSession");
            unloadedClasses.add("MainClientExec");
            unloadedClasses.add("AbstractAuthenticationHandler");
            unloadedClasses.add("CommonCookieAttributeHandler");
            unloadedClasses.add("DefaultProxyAuthenticationHandler");
            unloadedClasses.add("NullBackoffStrategy");
            unloadedClasses.add("HttpRoutePlanner");
            unloadedClasses.add("AuthScheme");
            unloadedClasses.add("HttpInetSocketAddress");
            unloadedClasses.add("ProxySelectorRoutePlanner");
            unloadedClasses.add("SystemDefaultRoutePlanner");
            unloadedClasses.add("TrustStrategy");
            unloadedClasses.add("BasicClientConnectionManager");
            unloadedClasses.add("BasicPoolEntry");
            unloadedClasses.add("RFC2965DiscardAttributeHandler");
            unloadedClasses.add("NegotiateSchemeFactory");
            unloadedClasses.add("CookieRestrictionViolationException");
            unloadedClasses.add("HttpRequestRetryHandler");
            unloadedClasses.add("AuthSchemeRegistry$1");
            unloadedClasses.add("DefaultRoutePlanner");
            unloadedClasses.add("LaxRedirectStrategy");
            unloadedClasses.add("RFC2109DomainHandler");
            unloadedClasses.add("LayeredSocketFactoryAdaptor");
            unloadedClasses.add("HttpConnPool$InternalConnFactory");
            unloadedClasses.add("RFC6265CookieSpecBase");
            unloadedClasses.add("HttpAuthenticator");
            unloadedClasses.add("BestMatchSpecFactory");
            unloadedClasses.add("RequestConfig");
            unloadedClasses.add("AbstractPooledConnAdapter");
            unloadedClasses.add("RFC2109SpecFactory");
            unloadedClasses.add("ConnRouteParamBean");
            unloadedClasses.add("PlainConnectionSocketFactory");
            unloadedClasses.add("HttpRequestWrapper$HttpEntityEnclosingRequestWrapper");
            unloadedClasses.add("RouteInfo");
            unloadedClasses.add("HttpClientContext");
            unloadedClasses.add("ClientParamBean");
            unloadedClasses.add("RequestConfig$Builder");
            unloadedClasses.add("DefaultClientConnectionOperator");
            unloadedClasses.add("CookieSpec");
            unloadedClasses.add("HttpRoute");
            unloadedClasses.add("AuthScope");
            unloadedClasses.add("SystemDefaultHttpClient");
            unloadedClasses.add("NTLMEngineImpl$Type3Message");
            unloadedClasses.add("HttpRequestBase");
            unloadedClasses.add("AllClientPNames");
            unloadedClasses.add("BasicPathHandler");
            unloadedClasses.add("DefaultProxyRoutePlanner");
            unloadedClasses.add("DefaultTargetAuthenticationHandler");
            unloadedClasses.add("URIUtils");
            unloadedClasses.add("NTLMSchemeFactory");
            unloadedClasses.add("BasicSchemeFactory");
            unloadedClasses.add("ResponseEntityProxy");
            unloadedClasses.add("RFC2109Spec");
            unloadedClasses.add("BrowserCompatSpecFactory");
            unloadedClasses.add("RequestDirector");
            unloadedClasses.add("AbstractCookieAttributeHandler");
            unloadedClasses.add("SchemeLayeredSocketFactoryAdaptor2");
            unloadedClasses.add("RFC2965Spec");
            unloadedClasses.add("BasicResponseHandler");
            unloadedClasses.add("DefaultHttpClient");
            unloadedClasses.add("URIBuilder");
            unloadedClasses.add("LoggingSessionOutputBuffer");
            unloadedClasses.add("DefaultBackoffStrategy");
            unloadedClasses.add("HttpPoolEntry");
            unloadedClasses.add("SSLContextBuilder");
            unloadedClasses.add("RedirectHandler");
            unloadedClasses.add("FutureRequestExecutionService");
            unloadedClasses.add("NetscapeDraftSpecFactory");
            unloadedClasses.add("RedirectStrategy");
            unloadedClasses.add("BasicManagedEntity");
            unloadedClasses.add("NetscapeDraftHeaderParser");
            unloadedClasses.add("CookieSpecRegistry$1");
            unloadedClasses.add("RequestTargetAuthentication");
            unloadedClasses.add("BasicHttpClientConnectionManager$1");
            unloadedClasses.add("EntityEnclosingRequestWrapper$EntityWrapper");
            unloadedClasses.add("BasicScheme");
            unloadedClasses.add("BasicPooledConnAdapter");
            unloadedClasses.add("CircularRedirectException");
            unloadedClasses.add("DefaultHttpResponseParser");
            unloadedClasses.add("DefaultRequestDirector");
            unloadedClasses.add("HttpPut");
            unloadedClasses.add("LayeredConnectionSocketFactory");
            unloadedClasses.add("DecompressingHttpClient");
            unloadedClasses.add("HttpDelete");
            unloadedClasses.add("DefaultServiceUnavailableRetryStrategy");
            unloadedClasses.add("RequestAcceptEncoding");
            unloadedClasses.add("MinimalHttpClient");
            unloadedClasses.add("ManagedClientConnection");
            unloadedClasses.add("ResponseProcessCookies");
            unloadedClasses.add("HttpEntityEnclosingRequestBase");
            unloadedClasses.add("RequestWrapper");
            unloadedClasses.add("GGSSchemeBase");
            unloadedClasses.add("LaxExpiresHandler");
            unloadedClasses.add("NetscapeDraftSpecProvider");
            unloadedClasses.add("ConnectionRequest");
            unloadedClasses.add("LoggingManagedHttpClientConnection");
            unloadedClasses.add("ConnectionBackoffStrategy");
            unloadedClasses.add("AbstractClientConnAdapter");
            unloadedClasses.add("CloseableHttpResponseProxy");
            unloadedClasses.add("HttpPatch");
            unloadedClasses.add("HttpClientParamConfig");
            unloadedClasses.add("ConnectionKeepAliveStrategy");
            unloadedClasses.add("MalformedChallengeException");
            unloadedClasses.add("PoolingClientConnectionManager");
            unloadedClasses.add("ClientConnectionRequest");
            unloadedClasses.add("ResponseContentEncoding");
            unloadedClasses.add("ClientContextConfigurer");
            unloadedClasses.add("ProxyClient");
            unloadedClasses.add("DefaultRedirectHandler");
            unloadedClasses.add("RFC2965CommentUrlAttributeHandler");
            unloadedClasses.add("ResponseHandler");
            unloadedClasses.add("RetryExec");
            unloadedClasses.add("RequestBuilder");
            unloadedClasses.add("DefaultHttpRoutePlanner");
            unloadedClasses.add("RFC2109VersionHandler");
            unloadedClasses.add("DefaultHttpRequestRetryHandler");
            unloadedClasses.add("AuthSchemeProvider");
            unloadedClasses.add("AbstractCookieSpec");
            unloadedClasses.add("DefaultResponseParser");
            unloadedClasses.add("HttpGet");
            unloadedClasses.add("IgnoreSpecFactory");
            unloadedClasses.add("RFC2965DomainAttributeHandler");
            unloadedClasses.add("HttpOptions");
            unloadedClasses.add("PlainSocketFactory");
            unloadedClasses.add("SSLConnectionSocketFactory");
            unloadedClasses.add("AuthenticationException");
            unloadedClasses.add("PoolingHttpClientConnectionManager");
            unloadedClasses.add("ProxyAuthenticationStrategy");
            unloadedClasses.add("CookieSpecFactory");
            unloadedClasses.add("SchemeLayeredSocketFactoryAdaptor");
            unloadedClasses.add("HttpHostConnectException");
            unloadedClasses.add("NegotiateScheme");
            unloadedClasses.add("RFC6265CookieSpecProvider");
            unloadedClasses.add("HttpPost");
            unloadedClasses.add("RFC2965VersionAttributeHandler");
            unloadedClasses.add("RequestBuilder$InternalEntityEclosingRequest");
            unloadedClasses.add("HttpHead");
            unloadedClasses.add("AbstractExecutionAwareRequest$2");
            unloadedClasses.add("ManagedHttpClientConnectionFactory");
            unloadedClasses.add("DefaultCookieSpecProvider$1");
            unloadedClasses.add("RequestAuthCache");
            unloadedClasses.add("DeflateDecompressingEntity");
            unloadedClasses.add("DefaultHttpResponseParserFactory");
            unloadedClasses.add("TrustSelfSignedStrategy");
            unloadedClasses.add("NoopUserTokenHandler");
            unloadedClasses.add("ConnectionSocketFactory");
            unloadedClasses.add("CloseableHttpResponse");
            unloadedClasses.add("TunnelRefusedException");
            unloadedClasses.add("LayeredSchemeSocketFactory");
            unloadedClasses.add("SchemeSocketFactory");
            unloadedClasses.add("NTLMScheme");
            unloadedClasses.add("SingleClientConnManager$ConnAdapter");
            unloadedClasses.add("NTLMEngineImpl$HMACMD5");
            unloadedClasses.add("RequestClientConnControl");
            unloadedClasses.add("DigestSchemeFactory");
            unloadedClasses.add("DefaultClientConnection");
            unloadedClasses.add("GzipDecompressingEntity");
            unloadedClasses.add("MultihomePlainSocketFactory");
            unloadedClasses.add("SingleClientConnManager$PoolEntry");
            unloadedClasses.add("PublicSuffixFilter");
            unloadedClasses.add("ClientConnectionManagerFactory");
            unloadedClasses.add("NTLMEngineImpl");
            unloadedClasses.add("AbstractExecutionAwareRequest");
            unloadedClasses.add("AuthSchemeRegistry");
            unloadedClasses.add("BackoffStrategyExec");
            unloadedClasses.add("NTLMEngine");
            unloadedClasses.add("AuthenticationStrategyAdaptor");
            unloadedClasses.add("RFC2965SpecFactory");
            unloadedClasses.add("HttpClientConnectionOperator");
            unloadedClasses.add("BasicCommentHandler");
            unloadedClasses.add("NetscapeDomainHandler");
            unloadedClasses.add("HttpTrace");
            unloadedClasses.add("ProtocolExec");
            unloadedClasses.add("NTLMEngineImpl$CipherGen");
            unloadedClasses.add("AuthenticationStrategy");
            unloadedClasses.add("DefaultRedirectStrategyAdaptor");
            unloadedClasses.add("ContentEncodingHttpClient");
            unloadedClasses.add("ContextAwareAuthScheme");
            unloadedClasses.add("HttpClient");
            unloadedClasses.add("SchemeSocketFactoryAdaptor");
            unloadedClasses.add("DefaultHttpClientConnectionOperator");
            unloadedClasses.add("HttpResponseProxy");
            unloadedClasses.add("BasicDomainHandler");
            unloadedClasses.add("RFC2965PortAttributeHandler");
            unloadedClasses.add("BasicHttpClientConnectionManager");
            unloadedClasses.add("ConnectTimeoutException");
            unloadedClasses.add("DefaultCookieSpecProvider");
            unloadedClasses.add("DecompressingEntity");
            unloadedClasses.add("CookieSpecRegistries");
            unloadedClasses.add("HttpRequestWrapper");
            unloadedClasses.add("BasicSecureHandler");
            unloadedClasses.add("RoutedRequest");
            unloadedClasses.add("RFC6265StrictSpec");
            unloadedClasses.add("DefaultManagedHttpClientConnection");
            unloadedClasses.add("AbstractResponseHandler");
            unloadedClasses.add("BrowserCompatSpec$1");
            unloadedClasses.add("AuthSchemeBase");
            unloadedClasses.add("HttpClientConnectionManager");
            unloadedClasses.add("NTLMEngineImpl$NTLMMessage");
            unloadedClasses.add("RequestAddCookies");
            unloadedClasses.add("ManagedClientConnectionImpl");
            unloadedClasses.add("SPNegoSchemeFactory");
            unloadedClasses.add("ServiceUnavailableRetryExec");
            unloadedClasses.add("RequestProxyAuthentication");
            unloadedClasses.add("AuthenticationHandler");
            unloadedClasses.add("RequestBuilder$InternalRequest");
            unloadedClasses.add("ResponseAuthCache");
            unloadedClasses.add("ConnConnectionParamBean");
            unloadedClasses.add("RequestExpectContinue");
            unloadedClasses.add("UrlEncodedFormEntity");
            unloadedClasses.add("RedirectExec");
            unloadedClasses.add("EntityBuilder");
            unloadedClasses.add("DefaultConnectionKeepAliveStrategy");
            unloadedClasses.add("ClientParamsStack");
            unloadedClasses.add("IgnoreSpecProvider");
            unloadedClasses.add("ServiceUnavailableRetryStrategy");
            unloadedClasses.add("AuthSchemeFactory");
            unloadedClasses.add("MinimalHttpClient$1");
            unloadedClasses.add("RFC2965SpecProvider");
            unloadedClasses.add("HttpClientParams");
            unloadedClasses.add("LoggingSessionInputBuffer");
            unloadedClasses.add("HttpExecutionAware");
            unloadedClasses.add("CookieSpecRegistry");
            unloadedClasses.add("HttpConnectionFactory");
            unloadedClasses.add("PoolingHttpClientConnectionManager$InternalConnectionFactory");
            unloadedClasses.add("InternalHttpClient");
            unloadedClasses.add("CloseableHttpClient");
            unloadedClasses.add("RFC2617Scheme");
            unloadedClasses.add("AuthParamBean");
            unloadedClasses.add("RequestDefaultHeaders");
            unloadedClasses.add("AuthenticationStrategyImpl");
            unloadedClasses.add("URLEncodedUtils");
            unloadedClasses.add("MinimalClientExec");
            unloadedClasses.add("RouteTracker");
            unloadedClasses.add("SingleClientConnManager");
            unloadedClasses.add("UserTokenHandler");
            */
            for (Artifact ar : artifacts) {
                if (ar.type.contains("jar")) {
                    //    System.out.println("ccc " + repoPath + "/" + ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar");
                //    if(ar.artifactId.contains("httpcomponents"))
                    classMethods.putAll(DependencyTreeMethods.listClassMethods2(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//") + "/" + ar.groupId + "/" + ar.version + "/" + ar.groupId + "-" + ar.version + ".jar"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return classMethods;
        }
    }

    public static HashMap<String, HashMap<String, String[]>> listUsedMethods(HashSet<String> usedLibraries, HashMap<String, String> packages) {
        HashMap<String, HashMap<String, String[]>> usedMethods = new HashMap<>();

        try {

            for (String lb : usedLibraries) {
                //    System.out.println(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar");
                usedMethods.putAll(DependencyTreeMethods.listUsedClassMethods(packages.get(lb), lb));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return usedMethods;
        }
    }

    public static HashMap<String, HashMap<String, String[]>> listUsedMethods2(String usedLibrary, HashMap<String, String> packages) {
        HashMap<String, HashMap<String, String[]>> usedMethods = new HashMap<>();

        try {

            for (String lb : packages.keySet()) {
                //   System.out.println("000 " + lb + " " + usedLibrary);
                if (lb.contains("$")) {
                    lb = lb.substring(0, lb.indexOf("$"));
                }
                if (lb.contains(".")) {
                    lb = lb.subSequence(0, lb.lastIndexOf(".")).toString();
                }

                //    System.out.println(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar");
                if (lb.equals(usedLibrary)) {
                   // System.out.println("bbb " + lb + " " + usedLibrary);
                    usedMethods.putAll(DependencyTreeMethods.listUsedClassMethods(packages.get(lb), lb));
                }
            //    System.out.println("ccc " + lb + " " + usedLibrary);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return usedMethods;
        }
    }

    public static HashMap<String, HashMap<String, String[]>> listUsedTestMethods(HashSet<String> usedLibraries, String path) {
        HashMap<String, HashMap<String, String[]>> usedMethods = new HashMap<>();

        try {

            for (String lb : usedLibraries) {
                //    System.out.println(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar");
                usedMethods.putAll(DependencyTreeMethods.listUsedClassMethods(path, lb));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return usedMethods;
        }
    }

    public static HashMap<String, HashMap<String, String>> listFields() {
        HashMap<String, HashMap<String, String>> classFields = new HashMap<>();

        try {
            String repoPath = System.getProperty("MavenRepoPath");

            for (Artifact ar : artifacts) {
                if (ar.type.contains("jar")) {
                    //    System.out.println("AAA " + repoPath + "/" + ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar");
                    classFields.putAll(DependencyTreeMethods.listClassFields(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//") + "/" + ar.groupId + "/" + ar.version + "/" + ar.groupId + "-" + ar.version + ".jar"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return classFields;
        }
    }

    public static HashMap<String, String> listPackages() {
        HashMap<String, String> jarPackages = new HashMap<>();

        try {
            String repoPath = System.getProperty("MavenRepoPath");

            for (Artifact ar : artifacts) {
                if (ar.type.contains("jar")) {
                    //   System.out.println(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar");
                    if (ar.groupId.contains("wildfly-core-testsuite-shared") || ar.groupId.contains("wildfly-testsuite-shared")) {
                        testsuiteArtifactsPaths.add(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//") + "/" + ar.groupId + "/" + ar.version + "/" + ar.groupId + "-" + ar.version + ".jar");
                    }
                    DependencyTreeMethods.listJarPackages(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//") + "/" + ar.groupId + "/" + ar.version + "/" + ar.groupId + "-" + ar.version + ".jar", jarPackages);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return jarPackages;
        }
    }

    public static HashSet<String> listAvailablePackages() {
        HashSet<String> packages = new HashSet<String>();

        HashMap<String, String> jarPackages = DependencyTreeMethods.listPackages();

      //  System.out.println("jarPackages.size() : " + jarPackages.size());

        for (String jc : jarPackages.keySet()) {
            if (jc.lastIndexOf(".") != -1) {
                String packageName = jc.substring(0, jc.lastIndexOf("."));
                //    System.out.println("packageName : " + packageName);
                if (!packages.contains(packageName)) {
                    packages.add(packageName);
                }
            }
        }

        return packages;
    }

    public static HashSet<String> getSourceClasses() {
        HashSet<String> testClasses = new HashSet<>();

        String serverTestPath = System.getProperty("ServerDir");
        String coreTestPath = System.getProperty("CoreDir");

        JavaClassParser.fileDiscovery(serverTestPath);
        testClasses.addAll(JavaClassParser.filesTest);
        JavaClassParser.filesTest.clear();
        JavaClassParser.fileDiscovery(coreTestPath);
        testClasses.addAll(JavaClassParser.filesTest);
        JavaClassParser.filesTest.clear();

        return testClasses;
    }

    private static HashMap<String, String> listJarPackages(String path, HashMap<String, String> jarPackages) {

        try {
            if (path != null) {
                JarFile jarFile = new JarFile(path);

                URL[] urls = {new URL("jar:file:" + path + "!/")};

                Enumeration allEntries = jarFile.entries();
                while (allEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) allEntries.nextElement();
                    String name = entry.getName();

                    //    System.out.println("Entry Name 1 : " + name);
                    if (name.contains(".class")) {
                        jarPackages.put(name.substring(0, name.lastIndexOf(".")).replaceAll("/", "."), path);
                    } else {
                        jarPackages.put(name.replaceAll("/", "."), path);
                    }
                    //    System.out.println("nnn " + name);
                }
            }
        } catch (Exception e) {
            System.out.println(path + " is not available.");
            //  e.printStackTrace();
        } finally {
            return jarPackages;
        }
    }

    private static HashMap<String, String> listJarClassPaths(String path) {
        HashMap<String, String> jarClasses = new HashMap<>();

        try {
            if (path != null) {
                JarFile jarFile = new JarFile(path);

                URL[] urls = {new URL("jar:file:" + path + "!/")};

                Enumeration allEntries = jarFile.entries();
                while (allEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) allEntries.nextElement();
                    String name = entry.getName();

                    //    System.out.println("Entry Name 1 : " + name);
                    if (name.contains(".class")) {
                        jarClasses.put(name.replaceAll("/", ".").replaceAll(".class", ""), path);
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(path + " is not available.");
            //  e.printStackTrace();
        } finally {
            return jarClasses;
        }
    }

    private static HashMap<String, ArrayList<Class[]>> listJarClasses(String path) {
        HashMap<String, ArrayList<Class[]>> jarClasses = new HashMap<>();

        try {
            if (path != null) {
                JarFile jarFile = new JarFile(path);

                URL[] urls = {new URL("jar:file:" + path + "!/")};
                URLClassLoader cl = URLClassLoader.newInstance(urls);

                Enumeration allEntries = jarFile.entries();
                while (allEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) allEntries.nextElement();
                    String name = entry.getName();

                    //        System.out.println("Entry Name 2 : " + name);
                    if (name.contains(".class") && !name.contains("$")) {
                        name = name.substring(0, name.lastIndexOf(".class"));
                        name = name.replaceAll("/", ".");
                        name = name.replaceAll("-", ".");
                        try {
                            Class clas = cl.loadClass(name);
                            Constructor[] constructors = clas.getConstructors();
                            ArrayList<Class[]> constructorParams = new ArrayList<>();
                            for (Constructor c : constructors) {
                                Class[] parameterTypes = c.getParameterTypes();
                                constructorParams.add(parameterTypes);
                            }
                            jarClasses.put(name, constructorParams);
                        } catch (Exception ex) {
                            //    ex.printStackTrace();
                        }
                    }
                    //    System.out.println("nnn " + name);
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println(path + " is not available.");
        } finally {
            return jarClasses;
        }
    }

    private static HashMap<String, HashMap<String, String[]>> listClassMethods(String path) {
        HashMap<String, HashMap<String, String[]>> classMethods = new HashMap<>();

        try {
            if (path != null) {
                JarFile jarFile = new JarFile(path);

                URL[] urls = {new URL("jar:file:" + path + "!/")};
                URLClassLoader cl = URLClassLoader.newInstance(urls);

                Enumeration allEntries = jarFile.entries();
                while (allEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) allEntries.nextElement();
                    
                    String name = entry.getName();
                    
                    if(entry.isDirectory() || !name.endsWith(".class")){
                        continue;
                    }

                    if (name.contains(".class")) {
                        String name2 = name;
                        name2 = name2.substring(0, name.lastIndexOf(".class"));
                        name2 = name2.replaceAll("/", ".");
                        name2 = name2.replaceAll("-", ".");

                     //   if(name2.contains("hibernate"))
                         //       System.out.println("namee " + name);
                        
                        try {
                            Class clas = cl.loadClass(name);

                            
                            HashMap<String, String[]> allMethods = new HashMap<>();

                            for (Class<?> c = clas; c != null; c = c.getSuperclass()) {
                                for (Method method : c.getMethods()) {

                               //     if(name.contains("hibernate"))
                                //        System.out.println("methodd " + method.getName());
                                    if (!Modifier.toString(method.getModifiers()).contains("private")) {
                                        String[] params = new String[method.getParameterTypes().length];
                                        int j = 0;
                                        for (Class cc : method.getParameterTypes()) {
                                            params[j] = cc.toString();
                                            j++;
                                        }
                                        allMethods.put(method.getName(), params);
                                        allMethods.put(method.getName() + "_Return_Type", new String[]{method.getReturnType().toString()});
                                    }
                                }
                            }

                         //   for (Class c : clas.getInterfaces()) {
                         //       parseInterfaces(c, name, classMethods);
                         //   }

                            classMethods.put(name, allMethods);
                        } catch (Exception ex) {
                       //     if(name.contains("hibernate"))
                        //        ex.printStackTrace();
                        }
                    }

                }
            }
        } catch (Exception e) {
            //    e.printStackTrace();
            System.out.println(path + " is not available.");
        } finally {
            return classMethods;
        }
    }
    
    private static HashMap<String, HashMap<String, String[]>> listClassMethods2(String path) {
        HashMap<String, HashMap<String, String[]>> classMethods = new HashMap<>();

        try {
            if (path != null) {
                String dir = path.replaceAll(".jar", "");
                dir = dir.replaceAll("//", "/");
            //    System.out.println("dir : " +  dir);
                ArrayList<String> commands=new ArrayList<String>();
                commands.add("mkdir");
                commands.add("-m777");
                commands.add(dir);
                ProcessBuilder pb = new ProcessBuilder(commands);
                Process p = pb.start();
                while (p.isAlive());
                p.destroy();
                
            
                try {
                    JarExtract.jarExtract(Paths.get(path), Paths.get(dir));
                    commands=new ArrayList<String>();
                    commands.add("bash");
                    commands.add("-c");
                    commands.add("cd " + dir + " ; find -name '*.class' | xargs javap -p > jarMethods.txt ; chmod 777 jarMethods.txt");
                    pb = new ProcessBuilder(commands);
                    p = pb.start();
                    while (p.isAlive());
                    p.destroy(); 
                }catch (Exception e) {
                //    System.out.println(dir + " already exists ...");
                }
                
           
                
                
            //    if(path.contains("httpcomponents"))
            //        System.out.println("pathName : " + path);
                classMethods.putAll(getParsedJarMethods(dir + "/jarMethods.txt"));
        /*         HashSet<String> paths = DependencyTreeMethods.getDataClasses(dir);
             //   if(path.contains("httpcomponents"))
             //       for(String s : paths)
             //           System.out.println("paths : " + s);
                Iterator<String> setIterator = paths.iterator();
                while(setIterator.hasNext()){
             //       if(path.contains("httpcomponents")) 
             //           System.out.println("Start");
                    String name = setIterator.next();
              //       if(path.contains("httpcomponents"))
              //                  System.out.println("name2e " + name);
                   if (name.contains(".class")) {
                        String name2 = name;
                        name2 = name2.substring(0, name.lastIndexOf(".class"));
                        String className = name2.substring(name2.lastIndexOf("/")+1);
                        name2 = name2.replaceAll("/", ".");
                        name2 = name2.replaceAll("-", ".");

                        
                        if(unloadedClasses.contains(className)) {
                            continue;
                        }
                        
                    //    if(path.contains("httpcomponents"))
                    //            System.out.println("namee " + name + " " + className);
                        
                        try {
                            URL url = new File(dir).toURI().toURL();
		            URL[] urls = new URL[]{url};
                            
                            ClassLoader cl = new URLClassLoader(urls); 
                            String name3 = name.replaceAll(dir+"/", "");
                     //       if(dir.contains("httpcomponents")){
                     //           System.out.println("dir " + dir + " " + name3);
                      //      }
                            name3 = name3.replaceAll(".class", "");
                            name3 = name3.replaceAll("/", ".");
                            name3 = name3.replaceAll("-", ".");
                         //   if(path.contains("httpcomponents"))
                           //     System.out.println("nameee " + name3);
                        //    Class clas = Class.forName(nameCopy);
                            Class clas = cl.loadClass(name3);

                            HashMap<String, String[]> allMethods = new HashMap<>();

                            for (Class c = clas; c != null; c = c.getSuperclass()) {
  
                                Method[] mmm = null;
                            //    if(path.contains("httpcomponents")){
                                    try{
                                   //     if(path.contains("httpcomponents"))
                                   //         System.out.println("SSS0 " + c.getName());
                                        mmm = c.getMethods();
                                    //    System.out.println("SSS1 " + mmm);
                                    }catch(SecurityException x){
                                        
                                    }
                                    
                              //      if(name3.contains("CloseableHttpClient"))
                             //           System.out.println("SSS1 " + c.getName());
                             //   }
                                
                                if(mmm!=null) {
                                    for (Method method : mmm) {

                                    //    if(name3.contains("CloseableHttpClient"))
                                    //        System.out.println("methodd " + method.getName());
                                        if (!Modifier.toString(method.getModifiers()).contains("private")) {
                                            String[] params = new String[method.getParameterTypes().length];
                                            int j = 0;
                                            for (Class cc : method.getParameterTypes()) {
                                                params[j] = cc.toString();
                                                j++;
                                            }
                                            allMethods.put(method.getName(), params);
                                            allMethods.put(method.getName() + "_Return_Type", new String[]{method.getReturnType().toString()});
                                        }
                                    }
                                }
                            //     if(path.contains("httpcomponents"))
                             //   System.out.println("SSS2 " + c.getName());
                                 
                                        
                             //           if(path.contains("httpcomponents"))
                             //                System.out.println("SSS3 " + c.getName());
                                        
                                        Class[] ccc = null;
                            //    if(path.contains("httpcomponents")){
                                    try{
                                        
                                        ccc = c.getInterfaces();
                                    //    System.out.println("SSS1 " + mmm);
                                    }catch(Exception x){
                                        
                                    }
                   
                                        if (ccc!=null) {
                                            for (Class cc : ccc) {
                                  //              if(path.contains("httpcomponents"))
                                  //               System.out.println("SSS4 " + c.getName());
   
                                 //               if(path.contains("httpcomponents"))
                                  //                  System.out.println("SSS11 " + cc.getName());

                                                Method[] mm = null;
                                        //    if(path.contains("httpcomponents")){
                                                try{

                                                    mm = cc.getMethods();
                                                //    System.out.println("SSS1 " + mmm);
                                                }catch(Exception x){

                                                }
                                    
                                                if(mm!=null) {
                                                    for (Method method2 : mm) {

                                                    //    if(name3.contains("CloseableHttpClient"))
                                                    //          System.out.println("methodb " + method2.getName());

                                                        if (!Modifier.toString(method2.getModifiers()).contains("private")) {
                                                            String[] params2 = new String[method2.getParameterTypes().length];
                                                            int j2 = 0;
                                                            for (Class cc2 : method2.getParameterTypes()) {
                                                                params2[j2] = cc2.toString();
                                                                j2++;
                                                            }
                                                            allMethods.put(method2.getName(), params2);
                                                            allMethods.put(method2.getName() + "_Return_Type", new String[]{method2.getReturnType().toString()});

                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    
                                
                            }

                         //   for (Class c : clas.getInterfaces()) {
                         //       parseInterfaces(c, name, classMethods);
                         //   }

                            classMethods.put(name2, allMethods);
                        } catch (Exception ex) {
                            
                        }
                    }
                    
              //      if(path.contains("httpcomponents")) 
              //          System.out.println("End");
                } */
            }
        } catch (Exception e) {
                e.printStackTrace();
            System.out.println(path + " is not available.");
        //    e.printStackTrace();
        } finally {
            return classMethods;
        }
    }
    
    private static HashMap<String, HashMap<String, String[]>> getParsedJarMethods(String file) {
        HashMap<String, HashMap<String, String[]>> classMethods = new HashMap<>();
        
    //    System.out.println("file " + file);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

        
            String line = br.readLine();
            String className = "";
            HashMap<String, String[]> methods = null;

            boolean record = false;
            while (line != null) {
                String[] params = null;
                String methodName = "";
                String returnType = "";
                String[] extensions = null;
                String[] extensions2 = null;
                if(record && !line.contains("private") && !line.contains("}") && !line.contains("class") && !line.contains("interface")){
                    if(line.contains(")")) {
                        line = line.substring(0,line.lastIndexOf(")"));
                        params = line.substring(line.indexOf("(")+1).split(", ");
                //        System.out.println("params " + Arrays.toString(params));
                        line = line.substring(0, line.indexOf("("));
                    }else if(line.contains("(")){
                         line = line.substring(0, line.indexOf("("));
                    }else
                        line = line.replaceAll(";", "");
                //    System.out.println("line " + line);
                    methodName = line.substring(line.lastIndexOf(" ")+1);
                    methods.put(methodName, params);
                 
                //    System.out.println("line " + line);
                    returnType = line.substring(0, line.lastIndexOf(methodName));
                    
                    if(returnType.contains("<"))
                        returnType = returnType.substring(0,returnType.indexOf("<"));
                    if(returnType.trim().contains(" ")) {
                    //    System.out.println("returnType " + returnType);
                        returnType = returnType.trim();
                        returnType = returnType.substring(returnType.lastIndexOf(" "));
                    //    System.out.println("returnType " + returnType);
                    }
                    if(returnType.contains("["))
                        returnType = returnType.substring(0,returnType.indexOf("["));
                    if(returnType.contains("."))
                        returnType = returnType.substring(returnType.lastIndexOf(".")+1);
                //    System.out.println("methodName " + line + " " + methodName);
                //    System.out.println("methodName " + line + " " + methodName);
                    methods.put(methodName +"_Return_Type", new String[]{returnType});
                    
                }
                
                if((line.contains("class") || line.contains("interface")) && line.contains("{")) {
            //        if(line.contains("DomainDeploymentManager"))
            //            System.out.println("Found ..................");
                    record = true;
                    if(line.contains("class"))
                        className = line.substring(line.indexOf("class")+6);
                    
                    if(line.contains("interface"))
                        className = line.substring(line.indexOf("interface")+10);
                    
                //    System.out.println("className " + className);
                    if(className.contains("extends")){
                        String extension = className.substring(className.lastIndexOf("extends")+8);
                        extension = extension.replaceAll("\\{", "");
                        if(extension.contains("implements")){
                            extension = extension.substring(0, extension.indexOf(" implements"));
                        }
                        extension = extension.trim();
                    //    System.out.println("Extension " + extension);
                        extensions = extension.split(",");
                        for(int i=0; i<extensions.length; i++) {
                            if(extensions[i].contains("<")){
                                extensions[i]=extensions[i].substring(0, extensions[i].indexOf("<"));
                            //    System.out.println("Extension " + extensions[i] + "of class " + className);
                            }else if(!extensions[i].contains("<") && extensions[i].contains(">")){
                                extensions[i]=null;
                            }
                        }
                    }
                    if(className.contains("implements")){
                        String extension2 = className.substring(className.lastIndexOf("implements")+11);
                        extension2 = extension2.replaceAll("\\{", "");
                        extension2 = extension2.trim();
                   //     System.out.println("Extension " + extension2);
                        extensions2 = extension2.split(",");
                        for(int i=0; i<extensions2.length; i++) {
                            if(extensions2[i].contains("<"))
                                extensions2[i]=extensions2[i].substring(0, extensions2[i].indexOf("<"));
                            else if(!extensions2[i].contains("<") && extensions2[i].contains(">")){
                                extensions2[i]=null;
                            }
                        }
                    }
                    
                    if(className.contains(" "))
                        className = className.substring(0,className.indexOf(" "));
                    if(className.contains("<"))
                        className = className.substring(0,className.indexOf("<"));
                    
                    methods = new HashMap<>();
                    
                    if(extensions!=null && extensions2!=null){
                        String[] both = Arrays.copyOf(extensions, extensions.length + extensions2.length);
                        System.arraycopy(extensions2, 0, both, extensions.length, extensions2.length);
                        methods.put(className + "_Extensions", both);
                    }else if(extensions!=null){
                        methods.put(className + "_Extensions", extensions);
                    }else if(extensions2!=null){
                        methods.put(className + "_Extensions", extensions2);
                    }
                }else if(line.contains("}")) {
                    record = false;
                    classMethods.put(className,methods);
               //     System.out.println("classExt : " + className + " " + methods.keySet().toString());
                }
                line = br.readLine();
            }
        }catch(Exception e){
            System.out.println("getParsedJarMethods : problem with file " + file);
        //    e.printStackTrace();
        }
        
        return classMethods;
    }
    
    public static HashSet<String> getDataClasses(String path) {
        HashSet<String> testClasses = new HashSet<>();

        JavaClassParser.classFileDiscovery(path);
        testClasses.addAll(JavaClassParser.filesTest);
        JavaClassParser.filesTest.clear();

        return testClasses;
    }

    private static void parseInterfaces(Class c, String name, HashMap<String, HashMap<String, String[]>> classMethods) throws MalformedURLException, IOException {
    //    System.out.println("in " + c.getName() + " " + name);
        String path2 = jarClassPaths.get(c.getName());
        if (path2 != null) {
            JarFile jarFile2 = new JarFile(path2);

            URL[] urls2 = {new URL("jar:file:" + path2 + "!/")};
            URLClassLoader cl2 = URLClassLoader.newInstance(urls2);

            Enumeration allEntries2 = jarFile2.entries();
            while (allEntries2.hasMoreElements()) {
                JarEntry entry2 = (JarEntry) allEntries2.nextElement();
                String name2 = entry2.getName();

                if (name2.contains(".class")) {
                    name2 = name2.substring(0, name2.lastIndexOf(".class"));
                    name2 = name2.replaceAll("/", ".");
                    name2 = name2.replaceAll("-", ".");

                    try {
                        Class clas2 = cl2.loadClass(name2);

                        HashMap<String, String[]> allMethods2 = new HashMap<>();

                        for (Class<?> c2 = clas2; c2 != null; c2 = c2.getSuperclass()) {
                            for (Method method : c2.getMethods()) {

                                if (!Modifier.toString(method.getModifiers()).contains("private")) {
                                    String[] params = new String[method.getParameterTypes().length];
                                    int j = 0;
                                    for (Class cc : method.getParameterTypes()) {
                                        params[j] = cc.toString();
                                        j++;
                                    }
                                    allMethods2.put(method.getName(), params);
                                    allMethods2.put(method.getName() + "_Return_Type", new String[]{method.getReturnType().toString()});
                                }
                            }
                        }

                        for (Class c2 : clas2.getInterfaces()) {
                            parseInterfaces(c2, name, classMethods);
                        }
                        
                        classMethods.put(name, allMethods2);
                        //   System.out.println("nnnn " + name + " " + allMethods2.keySet().toString());
                    } catch (Exception ex) {
                        //    ex.printStackTrace();
                    }
                }

            }
        }
    }

    private static HashMap<String, HashMap<String, String[]>> listUsedClassMethods(String path, String lib) {
        HashMap<String, HashMap<String, String[]>> classMethods = new HashMap<>();

        try {
            if (path != null) {
                JarFile jarFile = new JarFile(path);

                URL[] urls = {new URL("jar:file:" + path + "!/")};
                URLClassLoader cl = URLClassLoader.newInstance(urls);

                Enumeration allEntries = jarFile.entries();
                //    System.out.println("path : " + path);
                while (allEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) allEntries.nextElement();
                    String name = entry.getName();

                    //    System.out.println("name : " + name + " lib : " + lib);
                    String packageName = name.replaceAll("-", ".");
                    //     if(packageName.contains("test")){
                    //         System.out.println("package name : " + packageName + " " + name + " " + lib);
                    //     }
                    if (name.contains(".class") && packageName.replaceAll("/", ".").contains(lib)) {
                        //       System.out.println("package name 2 : " + packageName);
                        name = name.substring(0, name.lastIndexOf(".class"));
                        name = name.replaceAll("/", ".");
                        name = name.replaceAll("-", ".");

                        //    if(name.equals("org.jboss.as.test.integration.management.util.CLIWrapper")) {
                        //        System.out.println("pn1 " + path + " " + name);
                        //   }
                        try {
                            Class clas = cl.loadClass(name);

                            HashMap<String, String[]> allMethods = new HashMap<>();

                            for (Class<?> c = clas; c != null; c = c.getSuperclass()) {

                                for (Method method : c.getMethods()) {

                                    if (!Modifier.toString(method.getModifiers()).contains("private")) {

                                        String[] params = new String[method.getParameterTypes().length];
                                        int j = 0;
                                        for (Class cc : method.getParameterTypes()) {
                                            params[j] = cc.toString();
                                            j++;
                                        }
                                        allMethods.put(method.getName(), params);
                                        allMethods.put(method.getName() + "_Return_Type", new String[]{method.getReturnType().toString()});
                                    }
                                }
                            }
                            classMethods.put(name, allMethods);
                        } catch (Exception ex) {
                            //    ex.printStackTrace();
                        }
                    }

                }
            }
        } catch (Exception e) {
            //    e.printStackTrace();
            System.out.println(path + " is not available.");
        } finally {
            return classMethods;
        }
    }

    private static HashMap<String, HashMap<String, String>> listClassFields(String path) {
        HashMap<String, HashMap<String, String>> classFields = new HashMap<>();

        try {
            if (path != null) {
                JarFile jarFile = new JarFile(path);

                URL[] urls = {new URL("jar:file:" + path + "!/")};
                URLClassLoader cl = URLClassLoader.newInstance(urls);

                Enumeration<JarEntry> allEntries = jarFile.entries();
                while (allEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) allEntries.nextElement();
                    String name = entry.getName();

                    //        System.out.println("Entry Name : " + name);
                    if (name.endsWith(".class") && !entry.isDirectory()) {
                        name = name.substring(0, name.lastIndexOf(".class"));
                        name = name.replaceAll("/", ".");
                        name = name.replaceAll("-", ".");

                        try {
                            Class clas = cl.loadClass(name);

                            HashMap<String, String> allFields = new HashMap<>();

                            for (Class<?> c = clas; c != null; c = c.getSuperclass()) {
                                for (Field f : c.getFields()) {
                                    if (!Modifier.toString(f.getModifiers()).contains("private")) {
                                        if (!allFields.keySet().contains(f)) {
                                            allFields.put(f.getName(), f.getType().toString());
                                        }
                                    }
                                }
                            }
                            classFields.put(name, allFields);
                        } catch (Exception ex) {
                            //    ex.printStackTrace();
                        }
                    }

                }
                jarFile.close();
            }
        } catch (Exception e) {
            //    e.printStackTrace();
            System.out.println(path + " is not available.");
        } finally {
            return classFields;
        }
    }

}

class Artifact {

    String groupId;
    String artifactId;
    String version;
    String type;
    String scope;
}
