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
	String name = "�������� �Է����ּ���.";
	String email;

	// �����ߴ��� �˻��ϴ� ����
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

	// ���� �������� ����
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

		// ��ġ �Է�
		editUniv = (EditText) findViewById(R.id.editUniv);
		editPhone = (EditText) findViewById(R.id.editPhone);
		editName = (EditText) findViewById(R.id.editName);
		// deitEmail = (EditText) findViewById(R.id.editEmail);
		// ������ ������ Ŭ���ϸ� ������ �Է��� �� �ֵ���
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
			name = "�̸�";
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
				name = "ȫ�浿";

			if (profileDatas.size() > 0) {
				if (pm_pd.getPhoto() != null && imageInByte == null) {
					imageInByte = pm_pd.getPhoto();
				}

			}

			if (univ.getBytes().length <= 0 || phone.getBytes().length <= 0
					|| imageInByte == null) {

				String toast_s = "";

				if (univ.getBytes().length <= 0) {
					toast_s += " �б�";
				}
				if (phone.getBytes().length <= 0) {
					toast_s += " ��ȣ";
				}
				if (imageInByte == null) {
					toast_s += " ����";
				}

				toast_s += " ������ �Է����ּ���!!!";
				toast = Toast.makeText(getApplicationContext(), toast_s,
						Toast.LENGTH_LONG);
				toast.show();
				break;

			}

			// Profile.profilePhoto_default.setImageBitmap(photo);

			title = "�츮 ���� ����";
			text = "�������� �Բ���� ���� ���� ��!!";

			if (profileDatas.size() == 0) {
				
				initializePushNotification();
				
				// parse db�� �����ϱ�--> ó�� ������ ���� �ÿ� �����
				final ParseUser user = new ParseUser();
				user.setUsername(name);
				user.setPassword("");
				user.put("phoneNumber",phone);
				user.signUpInBackground(new SignUpCallback() {
					public void done(ParseException e) {
						if (e == null) {
							parseToast = Toast.makeText(getApplicationContext(), "������ ����Ǿ����ϴ�",Toast.LENGTH_LONG);
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
			//�������� �����ϴ� �� ó���� �ƴ� ���
			else {
				//���� �ֱ� ������ �ҷ���
				ParseUser currentUser = ParseUser.getCurrentUser();
				if (currentUser != null) {
					try {
						//���õ�� ����� ���� �������� �̸�(namebefore)�� �˻�
						ProfileData namebefore = profileDatas.get(profileDatas.size() - 2);
						currentUser = ParseUser.logIn(namebefore.getName(), ""); //�̸� & �н�����
					} catch (ParseException e) {
						e.printStackTrace();
					}
					currentUser.setUsername(name); //���� ����ڰ� �Է��� �� ����
					currentUser.put("phoneNumber", phone);
					currentUser.saveInBackground(); // This succeeds, since the user was authenticated on the device
				} else {
					parseToast = Toast.makeText(getApplicationContext(), "�������� ������ ������� �ʾҽ��ϴ�. �ٽ� �õ��ϼ���.",Toast.LENGTH_LONG);
					parseToast.show();					
				}
			}
			
			//local db�� �� �����ϱ�
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

	// ���� �������� �Լ�
	private void doTakePhotoAction() {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// �ӽ÷� ����� ������ ��θ� ����
		String url = "tmpImage.jpg";
		mImageCaptureUri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), url));
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				mImageCaptureUri);
		// Ư����⿡�� ������ ������ϴ� ������ �־� ������ �ּ�ó�� �մϴ�.
		// intent.putExtra("return-data", true);
		startActivityForResult(intent, PICK_FROM_CAMERA);

	}

	// �ٹ����� �̹��� ��������

	private void doTakeAlbumAction() {
		// �ٹ� ȣ��
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
			// ũ���� �� ������ �̹����� �Ѱ� �޽��ϴ�.
			// �̹����信 �̹����� �����شٰų� �ΰ����� �۾� ���Ŀ�
			// �ӽ� ������ �����մϴ�.
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
			// ������ ó���� ī�޶�� �����Ƿ� �ϴ� break���� �����մϴ�.
			// ���� �ڵ忡���� ���� �ո����� ����� �����Ͻñ� �ٶ��ϴ�.

			mImageCaptureUri = data.getData();
		}

		case PICK_FROM_CAMERA: {
			// �̹����� ������ ������ ���������� �̹��� ũ�⸦ �����մϴ�.
			// ���Ŀ� �̹��� ũ�� ���ø����̼��� ȣ���ϰ� �˴ϴ�.

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

		new AlertDialog.Builder(this).setTitle("���ε��� �̹��� ����")
				.setPositiveButton("�����Կ�", cameraListener)
				.setNeutralButton("�ٹ�����", albumListener)
				.setNegativeButton("���", cancelListener).show();
	}
	
	public void initializePushNotification() {
		ParseInstallation installation = ParseInstallation
				.getCurrentInstallation();
		
		installation.put("phoneNumber", phone);
		installation.put("wantPush", true);
		installation.saveInBackground();
	}

}
