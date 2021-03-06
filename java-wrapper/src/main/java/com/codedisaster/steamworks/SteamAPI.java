package com.codedisaster.steamworks;

import java.io.PrintStream;

public class SteamAPI {

	private static boolean isRunning = false;

	public static boolean init() throws SteamException {
		return init(null);
	}

	public static boolean init(String libraryPath) throws SteamException {

		SteamSharedLibraryLoader.loadLibraries(libraryPath);

		isRunning = nativeInit();

		return isRunning;
	}

	public static void shutdown() {
		isRunning = false;
		nativeShutdown();
	}

	/**
	 * According to the documentation, SteamAPI_IsSteamRunning() should "only be used in very
	 * specific cases". Also, there seems to be an issue with leaking OS resources on Mac OS X.
	 * For these reasons, the default behaviour of this function has been changed to <i>not</i>
	 * call the native function.
	 *
	 * @see SteamAPI#isSteamRunning(boolean)
	 */
	public static boolean isSteamRunning() {
		return isSteamRunning(false);
	}

	public static boolean isSteamRunning(boolean checkNative) {
		return isRunning && (!checkNative || isSteamRunningNative());
	}

	public static void printDebugInfo(PrintStream stream) {
		if (SteamSharedLibraryLoader.alreadyLoaded) {
			stream.println("  shared libraries loaded from: " + SteamSharedLibraryLoader.librarySystemPath);
		} else {
			stream.println("  shared libraries not loaded");
		}
		stream.println("  Steam API initialized: " + isRunning);
		stream.println("  Steam client active: " + isSteamRunning());
	}

	// @off

	/*JNI
		#include <steam_api.h>

		static JavaVM* staticVM = 0;
	*/

	public static native boolean restartAppIfNecessary(int appId); /*
		return SteamAPI_RestartAppIfNecessary(appId);
	*/

	public static native void releaseCurrentThreadMemory(); /*
		SteamAPI_ReleaseCurrentThreadMemory();
	*/

	private static native boolean nativeInit(); /*
		if (env->GetJavaVM(&staticVM) != 0) {
			return false;
		}

		return SteamAPI_Init();
	*/

	private static native void nativeShutdown(); /*
		SteamAPI_Shutdown();
	*/

	public static native void runCallbacks(); /*
		SteamAPI_RunCallbacks();
	*/

	private static native boolean isSteamRunningNative(); /*
		return SteamAPI_IsSteamRunning();
	*/

	static native long getSteamAppsPointer(); /*
		return (intp) SteamApps();
	*/

	static native long getSteamControllerPointer(); /*
		return (intp) SteamController();
	*/

	static native long getSteamFriendsPointer(); /*
		return (intp) SteamFriends();
	*/

	static native long getSteamHTTPPointer(); /*
		return (intp) SteamHTTP();
	*/

	static native long getSteamMatchmakingPointer(); /*
		return (intp) SteamMatchmaking();
	*/

	static native long getSteamNetworkingPointer(); /*
		return (intp) SteamNetworking();
	*/

	static native long getSteamRemoteStoragePointer(); /*
		return (intp) SteamRemoteStorage();
	*/

	static native long getSteamUGCPointer(); /*
		return (intp) SteamUGC();
	*/

	static native long getSteamUserPointer(); /*
		return (intp) SteamUser();
	*/

	static native long getSteamUserStatsPointer(); /*
		return (intp) SteamUserStats();
	*/

	static native long getSteamUtilsPointer(); /*
		return (intp) SteamUtils();
	*/

}
