/*
 * Copyright (C) 2013  Tsapalos Vasilios
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package info.bits.phoneastablet.receivers;

import java.io.IOException;

import info.bits.phoneastablet.services.OrientationService;
import info.bits.phoneastablet.utils.DatabaseHandler;
import info.bits.phoneastablet.utils.NotificationHandler;
import info.bits.phoneastablet.utils.SuCommands;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author LiTTle
 * This is a receiver which is called every time the device starts.
 */
public class BootCompletedReceiver extends BroadcastReceiver {

    /**
     * Change the device's settings according to user's preferences.
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent intent) {
	// TODO Auto-generated method stub
	DatabaseHandler dbHandler = new DatabaseHandler(context);
	NotificationHandler notificationHandler = new NotificationHandler(context);
	int policy = dbHandler.getResolutionPolicy();
	if (policy == DatabaseHandler.SERVICE_POLICY) {
	    notificationHandler.disableNotification();
	    context.startService(new Intent(context, OrientationService.class));
	}
	else if (policy == DatabaseHandler.NOTIFICATION_POLICY) {
	    notificationHandler.enableNotification();
	    context.stopService(new Intent(context, OrientationService.class));
	}
	else
	{
	    try {
		SuCommands.fallbackToDefaultResolution();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

}
