# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in c:\android-sdk\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn com.groovyandroid.NavigationDrawerFragment
-keepclassmembers class com.groovyandroid.NavigationDrawerFragment$* {*;}

# Groovy stuff so the compiler doesn't complain
-dontwarn org.codehaus.groovy.**
-dontwarn groovy**
-dontwarn com.vividsolutions.**
-dontwarn com.squareup.**
-dontwarn okio.**
-keep class org.codehaus.groovy.vmplugin.**
-keep class org.codehaus.groovy.runtime.**
-keepclassmembers class org.codehaus.groovy.runtime.dgm* {*;}
-keepclassmembers class ** implements org.codehaus.groovy.runtime.GeneratedClosure {*;}
-keepclassmembers class org.codehaus.groovy.reflection.GroovyClassValue* {*;}


# Don't shrink SwissKnife methods
-keep class com.arasthel.swissknife** { *; }

# Add this for any classes that will have SK injections
-keep class * extends android.app.Activity
-keepclassmembers class * extends android.app.Activity {*;}

-dontwarn android.**

-dontwarn groovyjarjaropenbeans.**
-keep class groovyjarjaropenbeans.**
-keepclassmembers class groovyjarjaropenbeans.** {*;}

# Include your own base package name if used with @CompileDynamic
-keep class dz.crystalbox.groovyandroidtemplate.**
-keepclassmembers class dz.crystalbox.groovyandroidtemplate.** {*;}