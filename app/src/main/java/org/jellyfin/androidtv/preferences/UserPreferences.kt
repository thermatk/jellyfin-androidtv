package org.jellyfin.androidtv.preferences

import android.content.Context
import android.preference.PreferenceManager

/**
 * User preferences are configurable by the user and change behavior of the application.
 * When changing preferences migration should be added to the init function.
 *
 * @param context Context to get the SharedPreferences from
 */
class UserPreferences(context: Context) : SharedPreferenceStore(PreferenceManager.getDefaultSharedPreferences(context)) {
	/* Authentication */
	/**
	 * Behavior for login when starting the app. Supports the following values:
	 * - 0: show login screen when starting the app
	 * - 1: login as the user who set this setting
	 *
	 * **note**: Currently settable via user-preferences only due too custom logic
	 */
	var loginBehavior by stringPreference("pref_login_behavior", "0")
		private set

	/**
	 * Ask for password when starting the app
	 */
	var passwordPromptEnabled by booleanPreference("pref_auto_pw_prompt", false)

	/**
	 * Use login using pin (when set)
	 */
	var passwordDPadEnabled by booleanPreference("pref_alt_pw_entry", false)

	/**
	 * Sign out automatically after x milliseconds
	 */
	var autoSignOutTimeout by stringPreference("pref_auto_logoff_timeout", "3600000")

	/* Display */
	/**
	 * Enable background images while browsing
	 */
	var backdropEnabled by booleanPreference("pref_show_backdrop", false)

	/**
	 * Show additional information for selected item while browsing
	 */
	var infoPanelEnabled by booleanPreference("pref_enable_info_panel", false)

	/**
	 * Show premieres on home screen
	 */
	var premieresEnabled by booleanPreference("pref_enable_premieres", false)

	/**
	 * Show a little notification to celebrate a set of holidays
	 */
	var seasonalGreetingsEnabled by booleanPreference("pref_enable_themes", false)

	/**
	 * Show additional debug information
	 */
	var debuggingEnabled by booleanPreference("pref_enable_debug", false)

	/* Playback - General*/
	/**
	 * Maximum bitrate in megabit for playback. A value of 0 means "auto".
	 */
	var maxBitrate by stringPreference("pref_max_bitrate", "0")

	/**
	 * Auto-play next item
	 */
	var mediaQueuingEnabled by booleanPreference("pref_enable_tv_queuing", false)

	/**
	 * Duration in seconds to subtract from resume time
	 */
	var resumeSubtractDuration by stringPreference("pref_resume_preroll", "0")

	/**
	 * Enable cinema mode
	 */
	var cinemaModeEnabled by booleanPreference("pref_enable_cinema_mode", false)

	/* Playback - Video */
	/**
	 * Preferred video player. Supports the following values:
	 * - auto (default): Automatically selects between exoplayer and vlc
	 * - exoplayer: Force ExoPlayer
	 * - vlc: Force libVLC
	 * - external: Use external player
	 */
	var videoPlayer by stringPreference("pref_video_player", "auto")

	/**
	 * Enable refresh rate switching when device supports it
	 */
	var refreshRateSwitchingEnabled by booleanPreference("pref_refresh_switching", false)

	/**
	 * Send a path instead to the external player
	 */
	var externalVideoPlayerSendPath by booleanPreference("pref_send_path_external", false)

	/* Playback - Audio related */
	/**
	 * Downmix audio. Values:
	 * 0: Direct
	 * 1: Downmix to Stereo
	 *
	 * When set to 1 audio will be downmixed.
	 * Disables the AC3, EAC3 and AAC_LATM audio codecs
	 */
	var audioOption by stringPreference("pref_audio_option", "0")

	/**
	 * Enable DTS
	 */
	var dtsEnabled by booleanPreference("pref_bitstream_dts", false)

	/**
	 * Enable AC3
	 */
	var ac3Enabled by booleanPreference("pref_bitstream_ac3", false)

	/* Live TV */
	/**
	 * Open live tv when opening the app
	 */
	var liveTvMode by booleanPreference("pref_live_tv_mode", false)

	/**
	 * Use direct play
	 */
	var liveTvDirectPlayEnabled by booleanPreference("pref_live_direct", false)

	/**
	 * Use VLC for live TV playback
	 */
	var liveTvUseVlc by booleanPreference("pref_enable_vlc_livetv", false)

	/**
	 * Use external player for live TV playback
	 */
	var liveTvUseExternalPlayer by booleanPreference("pref_live_tv_use_external", false)

	/* ACRA */
	/**
	 * Enable ACRA crash reporting
	 */
	var acraEnabled by booleanPreference("acra.enable", false)

	/**
	 * Never prompt to report crash logs
	 */
	var acraNoPrompt by booleanPreference("acra.alwaysaccept", false)

	/**
	 * Include system logs in crash reports
	 */
	var acraIncludeSystemLogs by booleanPreference("acra.syslog.enable", true)

	init {
		// Migrations
		migration(toVersion = 2) {
			// Migrate to new player preference
			val useExternal = it.getBoolean("pref_video_use_external", false)
			putString("pref_video_player", if (useExternal) "external" else "auto")
		}
	}
}
