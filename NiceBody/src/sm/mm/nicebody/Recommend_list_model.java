package sm.mm.nicebody;

import android.widget.ImageView;
import android.widget.TextView;

public class Recommend_list_model {

	int title;
	int image;

	public Recommend_list_model() {
		super();
	}

	public Recommend_list_model(int title, int image) {
		super();
		this.title = title;
		this.image = image;
	}

	public int getTitle() {
		return title;
	}

	public void setTitle(int title) {
		this.title = title;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

}
