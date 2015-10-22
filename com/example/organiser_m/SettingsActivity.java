package com.example.organiser_m;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.CreateFileActivityBuilder;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveApi.ContentsResult;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.MetadataChangeSet.Builder;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

public class SettingsActivity
  extends PreferenceActivity
  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
  private static final int REQUEST_CODE_BACKUP = 0;
  private static final int REQUEST_CODE_RESOLUTION = 2;
  private static final int REQUEST_CODE_RESTORE = 1;
  public String FileName;
  private DriveId driveId;
  EventChecker ev = new EventChecker();
  String filename;
  public GoogleApiClient mGoogleApiClient;
  private int sendCode;
  
  private void backupData()
  {
    Log.d("SettingsActivity", "Backing up");
    if (this.driveId == null) {
      return;
    }
    new EditDriveFileAsyncTask(this.mGoogleApiClient)
    {
      public EditDriveFileAsyncTask.Changes edit(Contents paramAnonymousContents)
      {
        MetadataChangeSet localMetadataChangeSet = new MetadataChangeSet.Builder().setTitle(SettingsActivity.this.FileName).build();
        try
        {
          byte[] arrayOfByte = MainActivity.fHandler.readFile().toString().getBytes();
          paramAnonymousContents.getOutputStream().write(arrayOfByte);
          return new EditDriveFileAsyncTask.Changes(this, localMetadataChangeSet, paramAnonymousContents);
        }
        catch (IOException localIOException)
        {
          for (;;)
          {
            Log.e("SettingsActivity", "Exception while reading from contents output stream", localIOException);
          }
        }
      }
      
      protected void onPostExecute(Status paramAnonymousStatus)
      {
        if (!paramAnonymousStatus.getStatus().isSuccess())
        {
          Toast.makeText(SettingsActivity.this.getApplicationContext(), "Error could not backup", 0).show();
          return;
        }
        Toast.makeText(SettingsActivity.this.getApplicationContext(), "Backed up", 0).show();
      }
    }.execute(new DriveId[] { this.driveId });
  }
  
  private void restoreData()
  {
    Log.d("SettingsActivity", "Restoring...");
    final DriveFile localDriveFile = Drive.DriveApi.getFile(this.mGoogleApiClient, this.driveId);
    localDriveFile.getMetadata(this.mGoogleApiClient).await();
    localDriveFile.openContents(this.mGoogleApiClient, 268435456, null).setResultCallback(new ResultCallback()
    {
      public void onResult(DriveApi.ContentsResult paramAnonymousContentsResult)
      {
        paramAnonymousContentsResult = paramAnonymousContentsResult.getContents();
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramAnonymousContentsResult.getInputStream()));
        StringBuilder localStringBuilder = new StringBuilder();
        for (;;)
        {
          try
          {
            str = localBufferedReader.readLine();
            if (str != null) {
              continue;
            }
          }
          catch (IOException localIOException)
          {
            String str;
            Log.e("SettingsActivity", "Error reading data contents" + localIOException);
            continue;
          }
          MainActivity.fHandler.OverwriteFile(localStringBuilder.toString());
          localDriveFile.discardContents(SettingsActivity.this.mGoogleApiClient, paramAnonymousContentsResult);
          return;
          localStringBuilder.append(str);
        }
      }
    });
  }
  
  private void setupActionBar()
  {
    getActionBar().setDisplayHomeAsUpEnabled(true);
  }
  
  private void showCustomizeMenu() {}
  
  private void showHistoryMenu()
  {
    startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
  }
  
  private void showSettingsMenu()
  {
    Intent localIntent = new Intent(getApplicationContext(), SettingsActivity.class);
    localIntent.putExtra("Filename", this.filename);
    startActivity(localIntent);
  }
  
  public void ResetData()
  {
    if (getApplicationContext().deleteFile(this.filename))
    {
      Toast.makeText(getApplicationContext(), "File Successfully Deleted", 0).show();
      MainActivity.fHandler.events.clear();
      MainActivity.fHandler.loadEventsFromFile();
      MainActivity.setColorsForEvents();
      new EventChecker().stop(getApplicationContext());
      return;
    }
    Toast.makeText(getApplicationContext(), "File Delete Failed! No File Found!", 0).show();
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    switch (paramInt1)
    {
    }
    do
    {
      do
      {
        do
        {
          return;
        } while (paramInt2 != -1);
        this.mGoogleApiClient.connect();
        return;
      } while (paramInt2 != -1);
      this.driveId = ((DriveId)paramIntent.getParcelableExtra("response_drive_id"));
      restoreData();
      return;
    } while (paramInt2 != -1);
    this.driveId = ((DriveId)paramIntent.getParcelableExtra("response_drive_id"));
    backupData();
  }
  
  public void onConnected(Bundle paramBundle)
  {
    Log.i("MainActivity", "API Client connected");
    if (this.sendCode == 2) {
      paramBundle = Drive.DriveApi.newOpenFileActivityBuilder().setMimeType(new String[] { "text/plain" }).build(this.mGoogleApiClient);
    }
    while (this.sendCode != 1) {
      try
      {
        startIntentSenderForResult(paramBundle, 1, null, 0, 0, 0);
        return;
      }
      catch (IntentSender.SendIntentException paramBundle)
      {
        Log.w("SettingsActivity", "Unable to send intent", paramBundle);
        return;
      }
    }
    paramBundle = new ResultCallback()
    {
      public void onResult(DriveApi.ContentsResult paramAnonymousContentsResult)
      {
        MetadataChangeSet localMetadataChangeSet = new MetadataChangeSet.Builder().setMimeType("text/plain").build();
        paramAnonymousContentsResult = Drive.DriveApi.newCreateFileActivityBuilder().setInitialMetadata(localMetadataChangeSet).setInitialContents(paramAnonymousContentsResult.getContents()).build(SettingsActivity.this.mGoogleApiClient);
        try
        {
          SettingsActivity.this.startIntentSenderForResult(paramAnonymousContentsResult, 0, null, 0, 0, 0);
          return;
        }
        catch (IntentSender.SendIntentException paramAnonymousContentsResult)
        {
          Log.w("SettingsActivity", "Unable to send intent", paramAnonymousContentsResult);
        }
      }
    };
    Drive.DriveApi.newContents(this.mGoogleApiClient).setResultCallback(paramBundle);
  }
  
  public void onConnectionFailed(ConnectionResult paramConnectionResult)
  {
    Log.i("MainActivity", "GoogleApiClient Connection failed");
    if (!paramConnectionResult.hasResolution())
    {
      GooglePlayServicesUtil.getErrorDialog(paramConnectionResult.getErrorCode(), this, 0).show();
      return;
    }
    try
    {
      paramConnectionResult.startResolutionForResult(this, 2);
      return;
    }
    catch (IntentSender.SendIntentException paramConnectionResult)
    {
      Log.e("MainActivity", "Exception while starting resolution activity", paramConnectionResult);
    }
  }
  
  public void onConnectionSuspended(int paramInt) {}
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(2130968576);
    PreferenceManager.setDefaultValues(this, 2130968576, false);
    setupActionBar();
    this.filename = getIntent().getStringExtra("Filename");
    findPreference("pref_key_reset_data").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        paramAnonymousPreference = new AlertDialog.Builder(SettingsActivity.this);
        paramAnonymousPreference.setMessage("Warning! This will delete all saved data");
        paramAnonymousPreference.setPositiveButton("Cancel", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            paramAnonymous2DialogInterface.cancel();
          }
        });
        paramAnonymousPreference.setNegativeButton("Confirm", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            SettingsActivity.this.ResetData();
          }
        });
        paramAnonymousPreference.show();
        return false;
      }
    });
    findPreference("pref_key_backup_data").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        paramAnonymousPreference = new AlertDialog.Builder(SettingsActivity.this);
        paramAnonymousPreference.setView(SettingsActivity.this.getLayoutInflater().inflate(2130903046, null)).setTitle("Enter Filename").setPositiveButton("Submit", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            paramAnonymous2DialogInterface = (EditText)((AlertDialog)paramAnonymous2DialogInterface).findViewById(2131099670);
            SettingsActivity.this.FileName = paramAnonymous2DialogInterface.getText().toString();
            SettingsActivity.this.mGoogleApiClient = new GoogleApiClient.Builder(SettingsActivity.this.getApplicationContext()).addApi(Drive.API).addScope(Drive.SCOPE_FILE).addConnectionCallbacks(SettingsActivity.this).addOnConnectionFailedListener(SettingsActivity.this).build();
            SettingsActivity.this.sendCode = 1;
            SettingsActivity.this.mGoogleApiClient.connect();
          }
        });
        paramAnonymousPreference.show();
        return false;
      }
    });
    findPreference("pref_key_restore_data").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        SettingsActivity.this.mGoogleApiClient = new GoogleApiClient.Builder(SettingsActivity.this.getApplicationContext()).addApi(Drive.API).addScope(Drive.SCOPE_FILE).addConnectionCallbacks(SettingsActivity.this).addOnConnectionFailedListener(SettingsActivity.this).build();
        SettingsActivity.this.sendCode = 2;
        SettingsActivity.this.mGoogleApiClient.connect();
        return false;
      }
    });
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131492866, paramMenu);
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 2131099702: 
      showSettingsMenu();
      return true;
    case 2131099703: 
      showHistoryMenu();
      return true;
    }
    NavUtils.navigateUpFromSameTask(this);
    return true;
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\example\organiser_m\SettingsActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */