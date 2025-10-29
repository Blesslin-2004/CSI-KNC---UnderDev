# --- Keep all annotations (important for Firestore, Compose, etc.) ---
-keepattributes *Annotation*
-keep class kotlin.Metadata { *; }

# --- Keep Jetpack Compose classes ---
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# --- Keep Firebase and Firestore model classes ---
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**
-keepclassmembers class * {
    @com.google.firebase.firestore.PropertyName <fields>;
}

# --- Keep your app's model/data classes (replace with your actual package if needed) ---
-keep class com.kpnorth.knc_app_csi.models.** { *; }

# --- If you use coroutines or flow ---
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

# --- Keep your Application class and MainActivity ---
-keep class com.kpnorth.knc_app_csi.MainActivity { *; }
-keep class com.kpnorth.knc_app_csi.**Application { *; }

# --- Keep line numbers for readable crash logs ---
-keepattributes SourceFile,LineNumberTable
