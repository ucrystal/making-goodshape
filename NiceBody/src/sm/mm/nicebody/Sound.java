package sm.mm.nicebody;

import android.content.Context;
import android.media.MediaPlayer;

class Sound {
	MediaPlayer mPlayer;

	Sound(Context context, int id) {
		mPlayer = MediaPlayer.create(context, id);
	}

	void play() {
		mPlayer.seekTo(0);
		mPlayer.start();
	}
}
