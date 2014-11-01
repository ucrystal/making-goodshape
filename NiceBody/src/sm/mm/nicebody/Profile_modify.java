package sm.mm.nicebody;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
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
	static String Height = null;
	static String Weight = null;
	static String Name = "ȫ�浿";
	
	//�����ߴ��� �˻��ϴ� ���� 
	static int checkInt = 0;

	static Bitmap photo;

	EditText editHeight;
	EditText editWeight;
	EditText editName;
	Toast toast;

	// ���� �������� ����
	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;

	private Uri mImageCaptureUri;
	private ImageView profilePhoto;
	private Button pickPhoto_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_modify);

		// ���� ȣ��
		pickPhoto_btn = (Button) findViewById(R.id.pickPhoto_btn);
		profilePhoto = (ImageView) findViewById(R.id.profilePhoto);

		pickPhoto_btn.setOnClickListener(this);

		// ��ġ �Է�
		editHeight = (EditText) findViewById(R.id.editHeight);
		editWeight = (EditText) findViewById(R.id.editWeight);
		editName = (EditText) findViewById(R.id.editName);

		modifyFns_btn = (Button) findViewById(R.id.modifyFinish_btn);
		modifyFns_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				
				Height = editHeight.getText().toString();
				Weight = editWeight.getText().toString();
				Name = editName.getText().toString();

				

				// ����(�����̽���)�� ������ �ѱ�� ���
				Height = Height.trim();
				Weight = Weight.trim();
				Name = Name.trim();
				
				
				if(Name.getBytes().length <= 0)
					Name = "ȫ�浿";

				if (Height.getBytes().length <= 0
						&& Weight.getBytes().length > 0) {// ���� �Ѿ�ö��� ó��

					toast = Toast.makeText(getApplicationContext(),
							"Ű�� �Է��ϼ���!", Toast.LENGTH_LONG);
					toast.show();
					return;

				} else if (Height.getBytes().length > 0
						&& Weight.getBytes().length <= 0) {

					toast = Toast.makeText(getApplicationContext(),
							"�����Ը� �Է��ϼ���!", Toast.LENGTH_LONG);
					toast.show();
					return;

				} else if (Height.getBytes().length <= 0
						&& Weight.getBytes().length <= 0) {

					toast = Toast.makeText(getApplicationContext(),
							"Ű�� �����Ը� �Է��ϼ���!", Toast.LENGTH_LONG);
					toast.show();
					return;

				}

				// Profile.profilePhoto_default.setImageBitmap(photo);

				Intent intent = new Intent(Profile_modify.this, Profile.class);
				startActivity(intent);
			}
		});

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
				profilePhoto.setImageBitmap(photo);
				
				//������ �������� checkInt�� 1�� ���� 
				checkInt = 1;

				// bitmap �̹��� file�� �����ϴ� ����
				try {

					File file = new File("profile.png");
					FileOutputStream fos = openFileOutput("profile.png", 0);
					photo.compress(CompressFormat.PNG, 100, fos);
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
}
