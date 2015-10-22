package com.example.organiser_m;

import android.os.AsyncTask;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveApi.ContentsResult;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource.MetadataResult;
import com.google.android.gms.drive.MetadataChangeSet;

public abstract class EditDriveFileAsyncTask
  extends AsyncTask<DriveId, Boolean, Status>
{
  private static final String TAG = "EditDriveFileAsyncTask";
  private GoogleApiClient mClient;
  
  public EditDriveFileAsyncTask(GoogleApiClient paramGoogleApiClient)
  {
    this.mClient = paramGoogleApiClient;
  }
  
  protected Status doInBackground(DriveId... paramVarArgs)
  {
    DriveFile localDriveFile = Drive.DriveApi.getFile(this.mClient, paramVarArgs[0]);
    paramVarArgs = localDriveFile.openContents(this.mClient, 536870912, null);
    Object localObject = (DriveApi.ContentsResult)paramVarArgs.await();
    if (!((DriveApi.ContentsResult)localObject).getStatus().isSuccess()) {
      return ((DriveApi.ContentsResult)paramVarArgs.await()).getStatus();
    }
    localObject = edit(((DriveApi.ContentsResult)localObject).getContents());
    paramVarArgs = null;
    if (((Changes)localObject).getMetadataChangeSet() != null)
    {
      DriveResource.MetadataResult localMetadataResult = (DriveResource.MetadataResult)localDriveFile.updateMetadata(this.mClient, ((Changes)localObject).getMetadataChangeSet()).await();
      if (!localMetadataResult.getStatus().isSuccess()) {
        return localMetadataResult.getStatus();
      }
    }
    if (((Changes)localObject).getContents() != null) {
      paramVarArgs = (Status)localDriveFile.commitAndCloseContents(this.mClient, ((Changes)localObject).getContents()).await();
    }
    return paramVarArgs.getStatus();
  }
  
  public abstract Changes edit(Contents paramContents);
  
  public class Changes
  {
    private Contents mContents;
    private MetadataChangeSet mMetadataChangeSet;
    
    public Changes(MetadataChangeSet paramMetadataChangeSet, Contents paramContents)
    {
      this.mMetadataChangeSet = paramMetadataChangeSet;
      this.mContents = paramContents;
    }
    
    public Contents getContents()
    {
      return this.mContents;
    }
    
    public MetadataChangeSet getMetadataChangeSet()
    {
      return this.mMetadataChangeSet;
    }
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\example\organiser_m\EditDriveFileAsyncTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */