package sm.mm.schedule_manager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SignUpCallback;

public class Profile_modify extends Activity implements OnClickListener {
	static String s_objectId;
	Button button_con;

	String univ;
	static String phone;
	String name = "프로필을 입력해주세요.";
	String email;

	// 수정했는지 검사하는 과정
	static int checkInt = 0;
	static Bitmap photo;
	static String title;
	static String text;

	EditText editUniv;
	EditText editPhone;
	EditText editName;
	EditText editEmail;
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

	private Toast parseToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_modify);
		
		profileDatas = new LinkedList<ProfileData>();
		profileDatas = Profile.db.getAllProfileDatas();

		customActionBar();

		if (profileDatas.size() != 0) {
			pm_pd = profileDatas.get(profileDatas.size() - 1);
		}

		// 수치 입력
		editUniv = (EditText) findViewById(R.id.editUniv);
		editPhone = (EditText) findViewById(R.id.editPhone);
		editName = (EditText) findViewById(R.id.editName);
		// deitEmail = (EditText) findViewById(R.id.editEmail);
		// 프로필 사진을 클릭하면 사진을 입력할 수 있도록
		profilePhoto = (ImageView) findViewById(R.id.profilePhoto);
		profilePhoto.setOnClickListener(this);

		if (Profile.db.checkTable() == 0) {

			univ = null;
			phone = null;

		} else if (Profile.db.checkTable() == 1) {

			editUniv.setText("" + pm_pd.getUniv());
			editPhone.setText("" + pm_pd.getPhone());
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

		if (name.getBytes().length <= 0)
			name = "이름";
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
			finish();
			break;
		case R.id.action_ok:

			univ = editUniv.getText().toString();
			phone = editPhone.getText().toString();
			name = editName.getText().toString();

			univ = univ.trim();
			phone = phone.trim();
			name = name.trim();

			if (name.getBytes().length <= 0)
				name = "홍길동";

			if (profileDatas.size() > 0) {
				if (pm_pd.getPhoto() != null && imageInByte == null) {
					imageInByte = pm_pd.getPhoto();
				}

			}

			if (univ.getBytes().length <= 0 || phone.getBytes().length <= 0
					|| imageInByte == null) {

				String toast_s = "";

				if (univ.getBytes().length <= 0) {
					toast_s += " 학교";
				}
				if (phone.getBytes().length <= 0) {
					toast_s += " 번호";
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

			// Profile.profilePhoto_default.setImageBitmap(photo);

			title = "우리 지금 만나";
			text = "우지만과 함께라면 편리한 공강 비교!!";

			if (profileDatas.size() == 0) {
				
				initializePushNotification();
				
				// parse db에 저장하기--> 처음 프로필 저장 시에 수행됨
				final ParseUser user = new ParseUser();
				user.setUsername(name);
				user.setPassword("");
				user.put("phoneNumber",phone);
				user.signUpInBackground(new SignUpCallback() {
					public void done(ParseException e) {
						if (e == null) {
							parseToast = Toast.makeText(getApplicationContext(), "서버에 저장되었습니다",Toast.LENGTH_LONG);
							parseToast.show();
							String objectId = user.getObjectId();
							if (objectId != null) {
								s_objectId = objectId;
								Log.v("test", objectId);
							} else
								Log.v("test", "NONONONONONO");
						} else {
							parseToast = Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG);
							parseToast.show();
						}
					}
				});

				
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put( "phone", phone );
				ParseCloud.callFunctionInBackground("notify", params,
						new FunctionCallback<String>() {
							public void done(String result, ParseException e) {
								if (e == null) {
									Log.v("parseTest", "sms result: <" + result
											+ ">");
								}
							}
						});
			}
			//프로필을 수정하는 게 처음이 아닐 경우
			else {
				//가장 최근 유저를 불러옴
				ParseUser currentUser = ParseUser.getCurrentUser();
				if (currentUser != null) {
					try {
						//로컬디비에 저장된 이전 프로필의 이름(namebefore)을 검색
						ProfileData namebefore = profileDatas.get(profileDatas.size() - 2);
						currentUser = ParseUser.logIn(namebefore.getName(), ""); //이름 & 패스워드
					} catch (ParseException e) {
						e.printStackTrace();
					}
					currentUser.setUsername(name); //현재 사용자가 입력한 값 저장
					currentUser.put("phoneNumber", phone);
					currentUser.saveInBackground(); // This succeeds, since the user was authenticated on the device
				} else {
					parseToast = Toast.makeText(getApplicationContext(), "프로필이 서버에 저장되지 않았습니다. 다시 시도하세요.",Toast.LENGTH_LONG);
					parseToast.show();					
				}
			}
			
			//local db에 값 저장하기
			ProfileData pd = new ProfileData(name, univ, phone, imageInByte);
			Profile.db.addProfileData(pd);

			intent = new Intent(Profile_modify.this, Profile.class);
			startActivity(intent);
			overridePendingTransition(R.anim.default_start_enter,
					R.anim.default_start_exit);
			finish();
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
	
	public void initializePushNotification() {
		ParseInstallation installation = ParseInstallation
				.getCurrentInstallation();
		
		installation.put("phoneNumber", phone);
		installation.put("wantPush", true);
		installation.saveInBackground();
	}

}
