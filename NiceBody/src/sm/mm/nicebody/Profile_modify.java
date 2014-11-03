package sm.mm.nicebody;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

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

	Button modifyFns_btn;
	
	static String Height;
	static String Weight;
	static String Name = "프로필을 입력해주세요.";
	
	//수정했는지 검사하는 과정 
	static int checkInt = 0;

	static Bitmap photo;

	EditText editHeight;
	EditText editWeight;
	EditText editName;
	Toast toast;

	// 사진 가져오는 변수
	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;

	private Uri mImageCaptureUri;
	private ImageView profilePhoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_modify);

		customActionBar();

		// 수치 입력
				editHeight = (EditText) findViewById(R.id.editHeight);
				editWeight = (EditText) findViewById(R.id.editWeight);
				editName = (EditText) findViewById(R.id.editName);
				
		// 사진 호출
		profilePhoto = (ImageView) findViewById(R.id.profilePhoto);

		profilePhoto.setOnClickListener(this);
		
		
		
		// 사진 호출

		if(Profile_modify.checkInt == 0){
			
			Height = null;
			Weight = null;
			
		}else if(Profile_modify.checkInt == 1) {
			
			editHeight.setText(Height);
			editWeight.setText(Weight);
			editName.setText(Name);
			
			try {
				
				String imgpath = "data/data/sm.mm.nicebody/files/profile.png";
				Bitmap bmp = BitmapFactory.decodeFile(imgpath);
				profilePhoto.setImageBitmap(bmp);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "load error",
						Toast.LENGTH_SHORT).show();
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
		// 특정기기에서 사진을 저장못하는 문제가 있어 다음을 주석처리 합니다.
		// intent.putExtra("return-data", true);
		startActivityForResult(intent, PICK_FROM_CAMERA);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
			break;
		case R.id.action_ok:
			
			Height = editHeight.getText().toString();
			Weight = editWeight.getText().toString();
			Name = editName.getText().toString();

			

			// 공백(스페이스바)만 눌러서 넘기는 경우
			Height = Height.trim();
			Weight = Weight.trim();
			Name = Name.trim();
			
			
			if(Name.getBytes().length <= 0)
				Name = "홍길동";

			if (Height.getBytes().length <= 0
					&& Weight.getBytes().length > 0) {// 빈값이 넘어올때의 처리

				toast = Toast.makeText(getApplicationContext(),
						"키를 입력하세요!", Toast.LENGTH_LONG);
				toast.show();
				break;


			} else if (Height.getBytes().length > 0
					&& Weight.getBytes().length <= 0) {

				toast = Toast.makeText(getApplicationContext(),
						"몸무게를 입력하세요!", Toast.LENGTH_LONG);
				toast.show();
				break;


			} else if (Height.getBytes().length <= 0
					&& Weight.getBytes().length <= 0) {

				toast = Toast.makeText(getApplicationContext(),
						"키와 몸무게를 입력하세요!", Toast.LENGTH_LONG);
				toast.show();
				break;

			}
			// Profile.profilePhoto_default.setImageBitmap(photo);
			
			checkInt = 1;
			
			intent = new Intent(this, Profile.class);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void customActionBar() {
		// Customize the ActionBar
		final ActionBar abar = getActionBar();
		abar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#67C6E5")));
		// abar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));//line
		// under the action bar
		View viewActionBar = getLayoutInflater().inflate(
				R.layout.actionbar_layout, null);
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				// Center the textview in the ActionBar !
				ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		TextView textviewTitle = (TextView) viewActionBar
				.findViewById(R.id.actionbar_textview);
		textviewTitle.setText(R.string.title_activity_profile_modify);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		// abar.setIcon(R.color.transparent);
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
				
				
				
				Bitmap output = Bitmap.createBitmap(photo.getWidth(),photo.getHeight(), Config.ARGB_8888);
				Canvas canvas = new Canvas(output);
				final Paint paint = new Paint();
				final Rect rect = new Rect(0, 0, photo.getWidth(), photo.getHeight());
				paint.setAntiAlias(true);
				canvas.drawARGB(0, 0, 0, 0);
				int size = (photo.getWidth() / 2);
				canvas.drawCircle(size, size, size, paint);
				paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
				canvas.drawBitmap(photo, rect, rect, paint);
				
				
				profilePhoto.setImageBitmap(output);
				
				//사진을 가져오면 checkInt를 1로 변경 
				checkInt = 1;

				// bitmap 이미지 file에 저장하는 과정
				try {

					File file = new File("profile.png");
					FileOutputStream fos = openFileOutput("profile.png", 0);
					output.compress(CompressFormat.PNG, 100, fos);
					fos.flush();
					fos.close();

				} catch (Exception e) {
					Toast.makeText(this, "file error", Toast.LENGTH_SHORT)
							.show();
				}
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
}
