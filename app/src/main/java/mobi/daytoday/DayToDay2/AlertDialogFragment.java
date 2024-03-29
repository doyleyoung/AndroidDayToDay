/*
* Copyright (C) 2012 - 2022 Doyle Young
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package mobi.daytoday.DayToDay2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment {
  private static final String TITLE_KEY = "title";
  
  /**
   * Constructor - Fragment requires public empty constructor
   */
  public AlertDialogFragment() {
    // nothing to see here
  }
  
	public static AlertDialogFragment newInstance(String title) {
		AlertDialogFragment alert = new AlertDialogFragment();
		Bundle args = new Bundle();
		args.putString(TITLE_KEY, title);
		alert.setArguments(args);
		return alert;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(getActivity())
		  .setIcon(android.R.drawable.ic_dialog_alert)
		  .setTitle(getArguments().getString(TITLE_KEY))
		  .setPositiveButton(getString(android.R.string.ok),
		      new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		          //nothing to see here
		        }
		      }
		  )
		  .show();
	}
}
