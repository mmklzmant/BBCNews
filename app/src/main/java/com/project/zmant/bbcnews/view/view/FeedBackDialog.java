package com.project.zmant.bbcnews.view.view;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.project.zmant.bbcnews.R;

public class FeedBackDialog {
	private Context mContext;
	private TextView tv_context;
	private Button yes;
	private View view;
	private LayoutInflater layoutInflater;
	private Dialog loadingDialog;
	private onClickDialogListener onClickDialogListener;

	public FeedBackDialog(Context context) {
		this.mContext = context;
		this.layoutInflater = LayoutInflater.from(context);
		onCreateView();
	}

	public void onCreateView() {
		view = layoutInflater.inflate(R.layout.feadback_dialog, null);
		tv_context = (TextView) view.findViewById(R.id.tv_feedback_content);
		yes = (Button) view.findViewById(R.id.btn_feedback_ok);
		loadingDialog = new Dialog(mContext, R.style.commondialog);
		
	}

	public void showDialog() {
		loadingDialog.show();
	}
	
	public void closeDialog(){
		
		loadingDialog.dismiss();
	}

	public void initUI( String content, String ok) {

		tv_context.setText(content);
		yes.setText(ok);

		yes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClickDialogListener.onClickOkButton();
			}
		});
		
		loadingDialog.setContentView(view);
	}

	public interface onClickDialogListener{
		
		public void onClickOkButton();

	}
	
	public void setOnDialogClickListener(onClickDialogListener onClickDialogListener) {
		this.onClickDialogListener = onClickDialogListener;
	}
}
