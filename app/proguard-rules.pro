-keep public class de.reckendrees.systems.tui.expert.commands.main.raw.* { public *; }
-keep public abstract class de.reckendrees.systems.tui.expert.commands.main.generals.* { public *; }
-keep public class de.reckendrees.systems.tui.expert.commands.tuixt.raw.* { public *; }
-keep public class de.reckendrees.systems.tui.expert.managers.notifications.NotificationService
-keep public class de.reckendrees.systems.tui.expert.managers.notifications.KeeperService
-keep public class de.reckendrees.systems.tui.expert.managers.options.**
-keep class de.reckendrees.systems.tui.expert.tuils.libsuperuser.**
-keep class de.reckendrees.systems.tui.expert.managers.suggestions.HideSuggestionViewValues
-keep public class it.andreuzzi.comparestring2.**

-dontwarn de.reckendrees.systems.tui.expert.commands.main.raw.**

-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn sun.misc.Unsafe

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

-dontwarn org.htmlcleaner.**
-dontwarn com.jayway.jsonpath.**
-dontwarn org.slf4j.**

-dontwarn org.jdom2.**