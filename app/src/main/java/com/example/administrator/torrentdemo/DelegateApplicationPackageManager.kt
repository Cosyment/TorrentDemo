package com.example.administrator.torrentdemo

import android.annotation.SuppressLint
import android.os.Build
import android.support.annotation.RequiresApi
import android.os.UserHandle
import android.content.ComponentName
import android.content.IntentFilter
import android.content.res.XmlResourceParser
import android.graphics.drawable.Drawable
import android.content.Intent
import android.annotation.TargetApi
import android.content.pm.*
import android.os.Bundle
import android.content.res.Resources
import android.graphics.Rect
import android.util.Log


/**
 *
 * author hechao
 * date 2018/6/28 0028
 */
@SuppressLint("NewApi")
class DelegateApplicationPackageManager : PackageManager {

    private val realPackageName = "com.example.administrator.torrentdemo"
    var mPackageManager: PackageManager

    constructor(packageManager: PackageManager) {
        this.mPackageManager = packageManager
    }

    override fun getPackageInfo(versionedPackage: VersionedPackage?, flags: Int): PackageInfo {
        return mPackageManager.getPackageInfo(versionedPackage, flags)
    }

    override fun getInstantAppCookie(): ByteArray {
        return mPackageManager.instantAppCookie
    }

    override fun getInstantAppCookieMaxBytes(): Int {
        return mPackageManager.instantAppCookieMaxBytes
    }

    override fun getSharedLibraries(flags: Int): MutableList<SharedLibraryInfo> {
        return mPackageManager.getSharedLibraries(flags)
    }

    override fun getChangedPackages(sequenceNumber: Int): ChangedPackages {
        return getChangedPackages(sequenceNumber)
    }

    override fun clearInstantAppCookie() {
        return mPackageManager.clearInstantAppCookie()
    }

    override fun updateInstantAppCookie(cookie: ByteArray?) {
        return mPackageManager.updateInstantAppCookie(cookie)
    }

    override fun setApplicationCategoryHint(packageName: String?, categoryHint: Int) {
        return mPackageManager.setApplicationCategoryHint(packageName, categoryHint)
    }

    override fun canRequestPackageInstalls(): Boolean {
        return mPackageManager.canRequestPackageInstalls()
    }

    override fun isInstantApp(): Boolean {
        return mPackageManager.isInstantApp
    }

    override fun isInstantApp(packageName: String?): Boolean {
        return mPackageManager.isInstantApp(packageName)
    }

    override fun getPackageInfo(packageName: String?, flags: Int): PackageInfo {
        val pi = mPackageManager.getPackageInfo(realPackageName, flags)
        pi.applicationInfo.packageName = packageName
        pi.packageName = packageName
        return pi
    }

    override fun currentToCanonicalPackageNames(names: Array<String>): Array<String> {
        return mPackageManager.currentToCanonicalPackageNames(names)
    }

    override fun canonicalToCurrentPackageNames(names: Array<String>): Array<String> {
        return mPackageManager.canonicalToCurrentPackageNames(names)
    }

    override fun getLaunchIntentForPackage(packageName: String): Intent? {
        return mPackageManager.getLaunchIntentForPackage(packageName)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun getLeanbackLaunchIntentForPackage(packageName: String): Intent? {
        return mPackageManager.getLeanbackLaunchIntentForPackage(packageName)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getPackageGids(packageName: String): IntArray {
        return getPackageGids(packageName, 0)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getPackageGids(packageName: String, flags: Int): IntArray {
        return getPackageGids(packageName, flags)
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Throws(PackageManager.NameNotFoundException::class)
    override fun getPackageUid(packageName: String, flags: Int): Int {
        return mPackageManager.getPackageUid(packageName, flags)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getPermissionInfo(name: String, flags: Int): PermissionInfo {
        return mPackageManager.getPermissionInfo(name, flags)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun queryPermissionsByGroup(group: String, flags: Int): List<PermissionInfo> {
        return mPackageManager.queryPermissionsByGroup(group, flags)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getPermissionGroupInfo(name: String,
                                        flags: Int): PermissionGroupInfo {
        return mPackageManager.getPermissionGroupInfo(name, flags)
    }

    override fun getAllPermissionGroups(flags: Int): List<PermissionGroupInfo> {
        return mPackageManager.getAllPermissionGroups(flags)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getApplicationInfo(packageName: String, flags: Int): ApplicationInfo {
        var packageName = packageName
        if ("com.xunlei.downloadprovider" == packageName) {
            packageName = realPackageName
        }
        val applicationInfo = mPackageManager.getApplicationInfo(packageName, flags)
        var metaData: Bundle? = applicationInfo.metaData
        if (metaData == null) {
            metaData = Bundle()
            applicationInfo.metaData = metaData
        }
        metaData.putString("com.xunlei.download.APP_KEY", "bpIzNjAxNTsxNTA0MDk0ODg4LjQyODAwMAOxNw==^a2cec7^10e7f1756b15519e20ffb6cf0fbf671f")
        return applicationInfo
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getActivityInfo(className: ComponentName, flags: Int): ActivityInfo {
        return mPackageManager.getActivityInfo(className, flags)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getReceiverInfo(className: ComponentName, flags: Int): ActivityInfo {
        return mPackageManager.getReceiverInfo(className, flags)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getServiceInfo(className: ComponentName, flags: Int): ServiceInfo {
        return mPackageManager.getServiceInfo(className, flags)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getProviderInfo(className: ComponentName, flags: Int): ProviderInfo {
        return mPackageManager.getProviderInfo(className, flags)
    }

    override fun getSystemSharedLibraryNames(): Array<String> {
        return mPackageManager.systemSharedLibraryNames
    }


    override fun getSystemAvailableFeatures(): Array<FeatureInfo> {
        return mPackageManager.systemAvailableFeatures
    }

    override fun hasSystemFeature(name: String): Boolean {
        return mPackageManager.hasSystemFeature(name)
    }

    @TargetApi(Build.VERSION_CODES.N)
    override fun hasSystemFeature(name: String, version: Int): Boolean {
        return mPackageManager.hasSystemFeature(name, version)
    }

    override fun checkPermission(permName: String, pkgName: String): Int {
        return mPackageManager.checkPermission(permName, pkgName)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun isPermissionRevokedByPolicy(permName: String, pkgName: String): Boolean {
        return mPackageManager.isPermissionRevokedByPolicy(permName, pkgName)
    }

    override fun addPermission(info: PermissionInfo): Boolean {
        return mPackageManager.addPermission(info)
    }

    override fun addPermissionAsync(info: PermissionInfo): Boolean {
        return mPackageManager.addPermissionAsync(info)
    }

    override fun removePermission(name: String) {
        mPackageManager.removePermission(name)
    }

    override fun checkSignatures(pkg1: String, pkg2: String): Int {
        return mPackageManager.checkSignatures(pkg1, pkg2)
    }

    override fun checkSignatures(uid1: Int, uid2: Int): Int {
        return mPackageManager.checkSignatures(uid1, uid2)
    }

    override fun getPackagesForUid(uid: Int): Array<String>? {
        return mPackageManager.getPackagesForUid(uid)
    }

    override fun getNameForUid(uid: Int): String? {
        return mPackageManager.getNameForUid(uid)
    }

    override fun getInstalledPackages(flags: Int): List<PackageInfo> {
        return mPackageManager.getInstalledPackages(flags)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun getPackagesHoldingPermissions(
            permissions: Array<String>, flags: Int): List<PackageInfo> {
        return mPackageManager.getPackagesHoldingPermissions(permissions, flags)
    }

    override fun getInstalledApplications(flags: Int): List<ApplicationInfo> {
        return mPackageManager.getInstalledApplications(flags)
    }

    override fun resolveActivity(intent: Intent, flags: Int): ResolveInfo? {
        val componentName = intent.component
        intent.component = ComponentName(realPackageName, componentName.className)
        intent.`package` = realPackageName
        return mPackageManager.resolveActivity(intent, flags)
    }

    override fun queryIntentActivities(intent: Intent,
                                       flags: Int): List<ResolveInfo> {
        return mPackageManager.queryIntentActivities(intent, flags)
    }


    override fun queryIntentActivityOptions(
            caller: ComponentName?, specifics: Array<Intent>?, intent: Intent,
            flags: Int): List<ResolveInfo> {
        return mPackageManager.queryIntentActivityOptions(caller, specifics, intent, flags)
    }

    override fun queryBroadcastReceivers(intent: Intent, flags: Int): List<ResolveInfo> {
        return mPackageManager.queryBroadcastReceivers(intent, flags)
    }

    override fun resolveService(intent: Intent, flags: Int): ResolveInfo {
        val componentName = intent.component
        intent.component = ComponentName(realPackageName, componentName.className)
        intent.`package` = realPackageName
        return mPackageManager.resolveService(intent, flags)
    }

    override fun queryIntentServices(intent: Intent, flags: Int): List<ResolveInfo> {
        return mPackageManager.queryIntentServices(intent, flags)
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun queryIntentContentProviders(intent: Intent, flags: Int): List<ResolveInfo> {
        return mPackageManager.queryIntentContentProviders(intent, flags)
    }

    override fun resolveContentProvider(name: String, flags: Int): ProviderInfo {
        return mPackageManager.resolveContentProvider(name, flags)
    }


    override fun queryContentProviders(processName: String,
                                       uid: Int, flags: Int): List<ProviderInfo> {
        return mPackageManager.queryContentProviders(processName, uid, flags)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getInstrumentationInfo(
            className: ComponentName, flags: Int): InstrumentationInfo {
        return mPackageManager.getInstrumentationInfo(className, flags)
    }

    override fun queryInstrumentation(
            targetPackage: String, flags: Int): List<InstrumentationInfo> {
        return mPackageManager.queryInstrumentation(targetPackage, flags)
    }

    override fun getDrawable(packageName: String, resId: Int,
                             appInfo: ApplicationInfo): Drawable {
        return mPackageManager.getDrawable(packageName, resId, appInfo)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getActivityIcon(activityName: ComponentName?): Drawable {
        return mPackageManager.getActivityIcon(activityName)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getActivityIcon(intent: Intent): Drawable {
        if (intent.component != null) {
            return getActivityIcon(intent.component)
        }

        val info = resolveActivity(
                intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (info != null) {
            return info.activityInfo.loadIcon(this)
        }

        throw PackageManager.NameNotFoundException(intent.toUri(0))
    }

    override fun getDefaultActivityIcon(): Drawable {
        return mPackageManager.defaultActivityIcon
    }

    override fun getApplicationIcon(info: ApplicationInfo): Drawable {
        return info.loadIcon(this)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getApplicationIcon(packageName: String): Drawable {
        return mPackageManager.getApplicationIcon(packageName)
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Throws(PackageManager.NameNotFoundException::class)
    override fun getActivityBanner(activityName: ComponentName): Drawable {
        return mPackageManager.getActivityBanner(activityName)
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Throws(PackageManager.NameNotFoundException::class)
    override fun getActivityBanner(intent: Intent): Drawable {
        return mPackageManager.getActivityBanner(intent)
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    override fun getApplicationBanner(info: ApplicationInfo): Drawable {
        return mPackageManager.getApplicationBanner(info)
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Throws(PackageManager.NameNotFoundException::class)
    override fun getApplicationBanner(packageName: String): Drawable {
        return mPackageManager.getApplicationBanner(packageName)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getActivityLogo(activityName: ComponentName?): Drawable {
        return mPackageManager.getActivityLogo(activityName)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getActivityLogo(intent: Intent): Drawable {
        if (intent.component != null) {
            return getActivityLogo(intent.component)
        }

        val info = resolveActivity(
                intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (info != null) {
            return info.activityInfo.loadLogo(this)
        }

        throw PackageManager.NameNotFoundException(intent.toUri(0))
    }

    override fun getApplicationLogo(info: ApplicationInfo): Drawable {
        return info.loadLogo(this)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getApplicationLogo(packageName: String): Drawable {
        return mPackageManager.getApplicationLogo(packageName)
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun getUserBadgedIcon(icon: Drawable, user: UserHandle): Drawable {
        return mPackageManager.getUserBadgedIcon(icon, user)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun getUserBadgedDrawableForDensity(drawable: Drawable, user: UserHandle,
                                                 badgeLocation: Rect, badgeDensity: Int): Drawable {
        return mPackageManager.getUserBadgedDrawableForDensity(drawable, user, badgeLocation, badgeDensity)
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun getUserBadgedLabel(label: CharSequence, user: UserHandle): CharSequence {
        return mPackageManager.getUserBadgedLabel(label, user)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getResourcesForActivity(activityName: ComponentName): Resources {
        return mPackageManager.getResourcesForActivity(activityName)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getResourcesForApplication(app: ApplicationInfo): Resources {
        return mPackageManager.getResourcesForApplication(app)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getResourcesForApplication(appPackageName: String): Resources {
        return mPackageManager.getResourcesForApplication(appPackageName)
    }


    override fun isSafeMode(): Boolean {
        return mPackageManager.isSafeMode
    }


    override fun getText(packageName: String, resid: Int,
                         appInfo: ApplicationInfo): CharSequence {
        return mPackageManager.getText(packageName, resid, appInfo)
    }

    override fun getXml(packageName: String, resid: Int,
                        appInfo: ApplicationInfo): XmlResourceParser {
        return mPackageManager.getXml(packageName, resid, appInfo)
    }

    override fun getApplicationLabel(info: ApplicationInfo): CharSequence {
        return mPackageManager.getApplicationLabel(info)
    }


    override fun verifyPendingInstall(id: Int, response: Int) {
        mPackageManager.verifyPendingInstall(id, response)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun extendVerificationTimeout(id: Int, verificationCodeAtTimeout: Int,
                                           millisecondsToDelay: Long) {
        mPackageManager.extendVerificationTimeout(id, verificationCodeAtTimeout, millisecondsToDelay)
    }


    override fun setInstallerPackageName(targetPackage: String,
                                         installerPackageName: String) {
        mPackageManager.setInstallerPackageName(targetPackage, installerPackageName)
    }

    override fun getInstallerPackageName(packageName: String): String {
        return mPackageManager.getInstallerPackageName(packageName)
    }


    override fun addPackageToPreferred(packageName: String) {
        mPackageManager.addPackageToPreferred(packageName)
    }

    override fun removePackageFromPreferred(packageName: String) {
        mPackageManager.removePackageFromPreferred(packageName)
    }

    override fun getPreferredPackages(flags: Int): List<PackageInfo> {
        return mPackageManager.getPreferredPackages(flags)
    }

    override fun addPreferredActivity(filter: IntentFilter,
                                      match: Int, set: Array<ComponentName>, activity: ComponentName) {
        mPackageManager.addPreferredActivity(filter, match, set, activity)
    }


    override fun clearPackagePreferredActivities(packageName: String) {
        mPackageManager.clearPackagePreferredActivities(packageName)
    }

    override fun getPreferredActivities(outFilters: List<IntentFilter>,
                                        outActivities: List<ComponentName>, packageName: String): Int {
        return mPackageManager.getPreferredActivities(outFilters, outActivities, packageName)
    }

    override fun setComponentEnabledSetting(componentName: ComponentName,
                                            newState: Int, flags: Int) {
        mPackageManager.setComponentEnabledSetting(componentName, newState, flags)
    }

    override fun getComponentEnabledSetting(componentName: ComponentName): Int {
        return mPackageManager.getComponentEnabledSetting(componentName)
    }

    override fun setApplicationEnabledSetting(packageName: String,
                                              newState: Int, flags: Int) {
        mPackageManager.setApplicationEnabledSetting(packageName, newState, flags)
    }

    override fun getApplicationEnabledSetting(packageName: String): Int {
        return mPackageManager.getApplicationEnabledSetting(packageName)
    }


    fun getApplicationHiddenSettingAsUser(packageName: String, user: UserHandle): Boolean {
        return getApplicationHiddenSettingAsUser(packageName, user)
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun getPackageInstaller(): PackageInstaller {
        return mPackageManager.packageInstaller
    }
}