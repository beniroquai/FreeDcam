package freed.cam.apis.camera2.modules;

import android.hardware.Camera;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;

import com.troop.freedcam.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import freed.cam.apis.basecamera.CameraWrapperInterface;

import freed.settings.SettingKeys;
import freed.settings.SettingsManager;
import freed.utils.Log;


public class CellStormModuleLocal extends PictureModuleApi2 {
    private final String TAG = CellStormModuleLocal.class.getSimpleName();

    private boolean continueCapture = false;
    private int cropSize = 100;


    public CellStormModuleLocal(CameraWrapperInterface cameraUiWrapper, Handler mBackgroundHandler, Handler mainHandler) {
        super(cameraUiWrapper, mBackgroundHandler, mainHandler);
        name = cameraUiWrapper.getActivityInterface().getStringFromRessources(R.string.module_cellstorm_local);
        Log.i(TAG, "This is cellSTORM1!");
    }



    @Override
    public void InitModule() {
        super.InitModule();

        // Set cropsize derived from settingsmanager

        try{
            String mCropsize = SettingsManager.get(SettingKeys.mCropsize).get();
            cropSize = Integer.parseInt(mCropsize);
        }
        catch(NumberFormatException e){
            cropSize = cropSize;
        }

    }


    @Override
    public String LongName() {
        return "CellStorm";
    }

    @Override
    public String ShortName() {
        return "Cell";
    }

    @Override
    public void DoWork() {
        Log.i(TAG, "This is cellSTORM!");
        if (continueCapture)
            continueCapture = false;
        else {
            continueCapture = true;
            super.DoWork();
        }
    }

    @Override
    protected void prepareCaptureBuilder(int captureNum) {
        currentCaptureHolder.setCropSize(cropSize, cropSize);
    }

    @Override
    public void internalFireOnWorkDone(File file) {
        fireOnWorkFinish(file);
    }
}