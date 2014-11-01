package sm.mm.nicebody;

public class Recommend_list_model {
	
	private int title;
	private int image;

	public Recommend_list_model()
	{
		super();
	}

	public Recommend_list_model(int title, int image)
	{
		super();
		this.title = title;
		this.image = image;
	}

	public int getTitle()
	{
		return title;
	}

	public void setTitle(int title)
	{
		this.title = title;
	}

	public int getImage()
	{
		return image;
	}

	public void setImage(int image)
	{
		this.image = image;
	}

}
