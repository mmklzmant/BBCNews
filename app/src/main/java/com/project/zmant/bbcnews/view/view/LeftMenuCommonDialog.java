package com.project.zmant.bbcnews.view.view;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.project.zmant.bbcnews.R;

public class LeftMenuCommonDialog {
	private Context mContext;
	private TextView tv_context;
	private Button yes, no;
	private View view;
	private LayoutInflater layoutInflater;
	private Dialog loadingDialog;
	private onClickDialogListener onClickDialogListener;

	public LeftMenuCommonDialog(Context context) {
		this.mContext = context;
		this.layoutInflater = LayoutInflater.from(context);
		onCreateView();
	}

	public void onCreateView() {
		view = layoutInflater.inflate(R.layout.leftmenu_common_dialog, null);
		tv_context = (TextView) view.findViewById(R.id.commondialog_content);
		yes = (Button) view.findViewById(R.id.commondialog_left_button);
		no = (Button) view.findViewById(R.id.commondialog_right_button);
		loadingDialog = new Dialog(mContext, R.style.commondialog);
		
	}

	public void showDialog() {
		loadingDialog.show();
	}
	
	public void closeDialog(){
		
		loadingDialog.dismiss();
	}

	public void initUI( String content, String left, String right) {

		tv_context.setText(content);
		no.setText(right);
		yes.setText(left);
		no.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClickDialogListener.onClickRightButton();
				
			}
		});
		
		yes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClickDialogListener.onClickLeftButton();
			}
		});
		
		loadingDialog.setContentView(view);
	}

	public interface onClickDialogListener{
		
		public void onClickLeftButton();
		public void onClickRightButton();
		
		
	}
	
	public void setOnDialogClickListener(onClickDialogListener onClickDialogListener) {
		this.onClickDialogListener = onClickDialogListener;
	}
}
