package server;
/*
public class Oauth {

    import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.CommandSeries;
import com.highmobility.autoapi.HighMobility;
import com.highmobility.autoapi.Telematics;
import com.highmobility.crypto.Crypto;
import com.highmobility.crypto.value.DeviceSerial;
import com.highmobility.crypto.value.Issuer;
import com.highmobility.crypto.value.PublicKey;
import com.highmobility.crypto.value.PrivateKey;
import com.highmobility.value.Bytes;
import com.highmobility.value.CarSerial;
import com.highmobility.value.DeviceCert;
import com.highmobility.value.DevicePrivateKey;
import com.highmobility.value.DevicePublicKey;
import com.highmobility.value.DeviceSerial;
import com.highmobility.value.Issuer;
import com.highmobility.value.PrivateKey;
import com.highmobility.value.PublicKey;

    // Replace these values with your own OAuth Client ID and Client Secret
    String clientId = "your_client_id";
    String clientSecret = "your_client_secret";

    // Replace this value with the unique identifier for your vehicle
    String vehicleId = "your_vehicle_id";

    // Create a new instance of the High Mobility Fleet Management client
    HighMobility highMobility = new HighMobility(clientId, clientSecret);

    // Authenticate with the High Mobility API using the OAuth flow
    String accessToken = highMobility.authenticate();

    // Retrieve vehicle data
    Telematics telematics = new Telematics(accessToken, vehicleId);
    CommandResolver resolver = new CommandResolver();
    CommandSeries commands = resolver.resolveCommands(telematics.getVehicleStatus().getValue());

    // Print the vehicle's fuel level
    Command fuelLevelCommand = commands.getFirstCommand(Command.FUEL_LEVEL);
System.out.println("Fuel level: " + fuelLevelCommand.getFuelLevel().getValue());

    // Send a command to the vehicle to lock the doors
    Command lockDoorsCommand = new Command.Builder(Command.LOCK_DOORS)
            .build();
telematics.sendCommand(lockDoorsCommand);

}

 */