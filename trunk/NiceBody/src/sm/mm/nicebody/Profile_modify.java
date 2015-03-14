package sm.mm.nicebody;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.PushService;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Profile_modify extends Activity implements OnClickListener {

	private BackPressCloseHandler backPressCloseHandler;
	Button modifyFns_btn;

	String Height;
	String Weight;
	String Name = "프로필을 입력해주세요.";

	// 수정했는지 검사하는 과정
	static int checkInt = 0;
	static Bitmap photo;
	static String title;
	static String text;

	EditText editHeight;
	EditText editWeight;
	EditText editName;
	Toast toast;

	byte[] imageInByte;

	// 사진 가져오는 변수
	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;

	private Uri mImageCaptureUri;
	private ImageView profilePhoto;

	List<ProfileData> profileDatas;
	ProfileData pm_pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_modify);

		customActionBar();
		backPressCloseHandler = new BackPressCloseHandler(this);

		profileDatas = new LinkedList<ProfileData>();
		profileDatas = Profile.db.getAllProfileDatas();

		if (profileDatas.size() != 0) {
			pm_pd = profileDatas.get(profileDatas.size() - 1);
		}

		// 수치 입력
		editHeight = (EditText) findViewById(R.id.editHeight);
		editWeight = (EditText) findViewById(R.id.editWeight);
		editName = (EditText) findViewById(R.id.editName);

		// 프로필 사진을 클릭하면 사진을 입력할 수 있도록
		profilePhoto = (ImageView) findViewById(R.id.profilePhoto);
		profilePhoto.setOnClickListener(this);

		if (Profile.db.checkTable() == 0) {

			Height = null;
			Weight = null;

		} else if (Profile.db.checkTable() == 1) {

			editHeight.setText("" + (int) pm_pd.getHeight());
			editWeight.setText("" + (int) pm_pd.getWeight());
			editName.setText("" + pm_pd.getName());

			if (pm_pd.getPhoto() == null) {
				Profile.profilePhoto_default = (ImageView) findViewById(R.id.profilePhoto_default);
			} else if (pm_pd.getPhoto() != null) {
				byte[] drawableIconByteArray = pm_pd.getPhoto();
				Bitmap bd = BitmapFactory.decodeByteArray(
						drawableIconByteArray, 0, drawableIconByteArray.length);

				profilePhoto.setImageBitmap(bd);
			}

		}

	}

	// 사진 가져오는 함수
	private void doTakePhotoAction() {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 임시로 사용할 파일의 경로를 생성
		String url = "tmpImage.jpg";
		mImageCaptureUri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), url));
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				mImageCaptureUri);

		startActivityForResult(intent, PICK_FROM_CAMERA);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.profile_modify, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, Main.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;
		case R.id.action_ok:

			Height = editHeight.getText().toString();
			Weight = editWeight.getText().toString();
			Name = editName.getText().toString();

			Height = Height.trim();
			Weight = Weight.trim();
			Name = Name.trim();

			if (Name.getBytes().length <= 0)
				Name = "홍길동";

			if (profileDatas.size() > 0) {
				if (pm_pd.getPhoto() != null && imageInByte == null) {
					imageInByte = pm_pd.getPhoto();
				}

			}

			if (Height.getBytes().length <= 0 || Weight.getBytes().length <= 0
					|| imageInByte == null) {

				String toast_s = "";

				if (Height.getBytes().length <= 0) {
					toast_s += " 키";
				}
				if (Weight.getBytes().length <= 0) {
					toast_s += " 몸무게";
				}
				if (imageInByte == null) {
					toast_s += " 사진";
				}

				toast_s += " 정보를 입력해주세요!!!";
				toast = Toast.makeText(getApplicationContext(), toast_s,
						Toast.LENGTH_LONG);
				toast.show();
				break;

			}

			title = "몸매가 예뻐gym";
			text = "우리와 함께 건강한 몸매 만들어봐요 ^0^";
			if (profileDatas.size() == 0) {

			}

			// db에 값 저장하기
			ProfileData pd = new ProfileData(Name, Integer.parseInt(Height),
					Integer.parseInt(Weight), imageInByte);
			Profile.db.addProfileData(pd);

			intent = new Intent(this, Profile.class);
			startActivity(intent);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void customActionBar() {
		final ActionBar abar = getActionBar();
		abar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#67C6E5")));
		View viewActionBar = getLayoutInflater().inflate(
				R.layout.actionbar_layout, null);
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		TextView textviewTitle = (TextView) viewActionBar
				.findViewById(R.id.actionbar_textview);
		textviewTitle.setText(R.string.title_activity_profile_modify);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		abar.setHomeButtonEnabled(true);
	}

	// 앨범에서 이미지 가져오기

	private void doTakeAlbumAction() {
		// 앨범 호출
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(intent, PICK_FROM_ALBUM);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case CROP_FROM_CAMERA: {
			// 크롭이 된 이후의 이미지를 넘겨 받습니다.
			// 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
			// 임시 파일을 삭제합니다.
			final Bundle extras = data.getExtras();

			if (extras != null) {
				photo = extras.getParcelable("data");

				Bitmap output = Bitmap.createBitmap(photo.getWidth(),
						photo.getHeight(), Config.ARGB_8888);
				Canvas canvas = new Canvas(output);
				final Paint paint = new Paint();
				final Rect rect = new Rect(0, 0, photo.getWidth(),
						photo.getHeight());
				paint.setAntiAlias(true);
				canvas.drawARGB(0, 0, 0, 0);
				int size = (photo.getWidth() / 2);
				canvas.drawCircle(size, size, size, paint);
				paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
				canvas.drawBitmap(photo, rect, rect, paint);

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				output.compress(Bitmap.CompressFormat.PNG, 100, stream);
				imageInByte = stream.toByteArray();

				profilePhoto.setImageBitmap(output);
			}

			break;
		}

		case PICK_FROM_ALBUM: {
			// 이후의 처리가 카메라와 같으므로 일단 break없이 진행합니다.
			// 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.

			mImageCaptureUri = data.getData();
		}

		case PICK_FROM_CAMERA: {
			// 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
			// 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.

			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(mImageCaptureUri, "image/*");

			intent.putExtra("outputX", 90);
			intent.putExtra("outputY", 90);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, CROP_FROM_CAMERA);

			break;
		}
		}
	}

	@Override
	public void onClick(View v) {
		DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				doTakePhotoAction();
			}
		};

		DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				doTakeAlbumAction();
			}
		};

		DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		};

		new AlertDialog.Builder(this).setTitle("업로드할 이미지 선택")
				.setPositiveButton("사진촬영", cameraListener)
				.setNeutralButton("앨범선택", albumListener)
				.setNegativeButton("취소", cancelListener).show();
	}

	@Override
	public void onBackPressed() {
		backPressCloseHandler.onBackPressed();
	}

	public void initializePushNotification() {
		ParseInstallation installation = ParseInstallation
				.getCurrentInstallation();

		installation.put("phoneNumber", "821042746727");
		installation.put("wantPush", true);
		installation.saveInBackground();
	}
}
