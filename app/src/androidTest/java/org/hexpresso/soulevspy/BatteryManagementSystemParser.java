package org.hexpresso.soulevspy;

import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-16.
 */
public class BatteryManagementSystemParser {

    private final String BMS_ECU = "7EC";

    public class Data {
        public double stateOfCharge;                // %
        public double stateOfHealth;                // %
        public double batteryDcVoltage;             // V
        public double maxCellVoltage;               // V
        public double minCellVoltage;               // V
        public double batteryCurrent;               // A
        public double availableChargePower;         // 'kW
        public double availableDischargePower;      // 'kW
        public double auxiliaryBatteryVoltage;      // V
        public int batteryModuleTemperature[] = new int[8]; // °C

        public int maxCellVoltageNo; // Cell #
        public int minCellVoltageNo; // Cell #
        public double accumulativeChargeCurrent; // Ah
        public double accumulativeDischargeCurrent; // Ah
        public double accumulativeChargePower; // kWh
        public double accumulativeDischargePower; // kWh
        public int accumulativeOperatingTime; // Sec

        // Status BMS main relay	NO	-
        // Adjustable state BMS	NO	-
        // BMS Warning	NO	-
        // BMS error	NO	-
        // Note BMS-blocking	NO	-
        // Battery - Max temperature.	10	°C
        // Battery - Min temperature.	10	°C
        // Battery input temperature	10	°C
        // Status BLOWER	0	-
        // Fan feedback frequency	0	Hz
        // Cumulative charging voltage	4009,7	Ah
        // Cumulative discharge	4097,5	Ah
        // Cumulative charging current	1471,9	kWh
        // Cumulative discharge	1465,8	kWh
        // Cumulative operating time	1744263	Sec
        // MCU ready	YES	-
        // Request MCU main relay from	NO	-
        // Controllable MCU	NO	-
        // HCU ready	YES	-
        // Capacitor Voltage Inverter	1?	V
        // Drive Motor Speed	0	rpm
        // Insulation resistance	1000?	kOhm

        public double batteryCellVoltage[] = new double[96]; // V

        // Battery cell voltage deviation	0,00	V
        // Fast charging normal status	NG	-
        // Airbag hardwiring duty	80	%
        // Temperature Heat 1	10	°C
        // Temperature Heat 2	10	°C
        // SOC display	88,0	%
    }

    Data bmsData = null;

    Data getParsedData() {
        return bmsData;
    }

    public BatteryManagementSystemParser() {
        bmsData = new Data();
    }

    public boolean parseMessage2101(String rawData) {
        ParsedRawData data = new ParsedRawData(rawData);
        if( !data.isValid() )
        {
            return false;
        }

        // Get the data blocks
        final ArrayList<String> line21 = data.getData("21");
        final ArrayList<String> line22 = data.getData("22");
        final ArrayList<String> line23 = data.getData("23");
        final ArrayList<String> line24 = data.getData("24");
        final ArrayList<String> line25 = data.getData("25");
        final ArrayList<String> line26 = data.getData("26");
        final ArrayList<String> line27 = data.getData("27");
        final ArrayList<String> line28 = data.getData("28");

        bmsData.stateOfCharge = HexToInteger(line21.get(0)) * 0.5;

        bmsData.batteryDcVoltage = ( ( HexToInteger(line22.get(1) ) << 8) + HexToInteger(line22.get(2)) ) * 0.1;
        bmsData.maxCellVoltage = HexToInteger(line23.get(5)) * 0.02;
        bmsData.minCellVoltage = HexToInteger(line24.get(0)) * 0.02;
        //bmsData.batteryCurrent = ( (data.getDataByte("21", 6) << 8) + (data.getDataByte("22", 0) )) * 0.1;
        bmsData.availableChargePower = ( ( HexToInteger(line21.get(1) ) << 8) + HexToInteger(line21.get(2)) ) * 0.01;
        bmsData.availableDischargePower = ( ( HexToInteger(line21.get(3) ) << 8) + HexToInteger(line21.get(4)) ) * 0.01;
        bmsData.auxiliaryBatteryVoltage = HexToInteger(line24.get(4)) * 0.1;

        bmsData.batteryModuleTemperature[0] = HexToInteger(line22.get(3));
        bmsData.batteryModuleTemperature[1] = HexToInteger(line22.get(4));
        bmsData.batteryModuleTemperature[2] = HexToInteger(line22.get(5));
        bmsData.batteryModuleTemperature[3] = HexToInteger(line22.get(6));
        bmsData.batteryModuleTemperature[4] = HexToInteger(line23.get(0));
        bmsData.batteryModuleTemperature[5] = HexToInteger(line23.get(1));
        bmsData.batteryModuleTemperature[6] = HexToInteger(line23.get(2));
        bmsData.batteryModuleTemperature[7] = HexToInteger(line23.get(4));

        bmsData.maxCellVoltageNo = HexToInteger(line23.get(6));
        bmsData.minCellVoltageNo = HexToInteger(line24.get(1));

        bmsData.accumulativeChargeCurrent = ( ( HexToInteger(line24.get(5) ) << 24) +
                                              ( HexToInteger(line24.get(6) ) << 16) +
                                              ( HexToInteger(line25.get(0) ) << 8) +
                                              ( HexToInteger(line25.get(1) ) ) ) * 0.1;
        bmsData.accumulativeDischargeCurrent = ( ( HexToInteger(line25.get(2) ) << 24) +
                                                 ( HexToInteger(line25.get(3) ) << 16) +
                                                 ( HexToInteger(line25.get(4) ) << 8) +
                                                 ( HexToInteger(line25.get(5) ) ) ) * 0.1;
        bmsData.accumulativeChargePower = ( ( HexToInteger(line25.get(6) ) << 24) +
                                            ( HexToInteger(line26.get(0) ) << 16) +
                                            ( HexToInteger(line26.get(1) ) << 8) +
                                            ( HexToInteger(line26.get(2) ) ) ) * 0.1;
        bmsData.accumulativeDischargePower = ( ( HexToInteger(line26.get(3) ) << 24) +
                                               ( HexToInteger(line26.get(4) ) << 16) +
                                               ( HexToInteger(line26.get(5) ) << 8) +
                                               ( HexToInteger(line26.get(6) ) ) ) * 0.1;
        bmsData.accumulativeOperatingTime = ( ( HexToInteger(line27.get(0) ) << 24) +
                                              ( HexToInteger(line27.get(1) ) << 16) +
                                              ( HexToInteger(line27.get(2) ) << 8) +
                                              ( HexToInteger(line27.get(3) ) ) );

        return true;
    }

    /*
            // Battery Cell Voltage 01-96
        bmsData.batteryCellVoltage[0] = HexToInteger(line21.get(0)) * 0.02;
        bmsData.batteryCellVoltage[1] = HexToInteger(line21.get(1)) * 0.02;
        bmsData.batteryCellVoltage[2] = HexToInteger(line21.get(2)) * 0.02;
        bmsData.batteryCellVoltage[3] = HexToInteger(line21.get(3)) * 0.02;
        bmsData.batteryCellVoltage[4] = HexToInteger(line21.get(4)) * 0.02;
        bmsData.batteryCellVoltage[5] = HexToInteger(line21.get(5)) * 0.02;
        bmsData.batteryCellVoltage[6] = HexToInteger(line21.get(6)) * 0.02;
        bmsData.batteryCellVoltage[7]  = HexToInteger(line22.get(0)) * 0.02;
        bmsData.batteryCellVoltage[8]  = HexToInteger(line22.get(1)) * 0.02;
        bmsData.batteryCellVoltage[9]  = HexToInteger(line22.get(2)) * 0.02;
        bmsData.batteryCellVoltage[10] = HexToInteger(line22.get(3)) * 0.02;
        bmsData.batteryCellVoltage[11] = HexToInteger(line22.get(4)) * 0.02;
        bmsData.batteryCellVoltage[12] = HexToInteger(line22.get(5)) * 0.02;
        bmsData.batteryCellVoltage[13] = HexToInteger(line22.get(6)) * 0.02;
        bmsData.batteryCellVoltage[14] = HexToInteger(line23.get(0)) * 0.02;
        bmsData.batteryCellVoltage[15] = HexToInteger(line23.get(1)) * 0.02;
        bmsData.batteryCellVoltage[16] = HexToInteger(line23.get(2)) * 0.02;
        bmsData.batteryCellVoltage[17] = HexToInteger(line23.get(3)) * 0.02;
        bmsData.batteryCellVoltage[18] = HexToInteger(line23.get(4)) * 0.02;
        bmsData.batteryCellVoltage[19] = HexToInteger(line23.get(5)) * 0.02;
        bmsData.batteryCellVoltage[20] = HexToInteger(line23.get(6)) * 0.02;
        bmsData.batteryCellVoltage[21] = HexToInteger(line24.get(0)) * 0.02;
        bmsData.batteryCellVoltage[22] = HexToInteger(line24.get(1)) * 0.02;
        bmsData.batteryCellVoltage[23] = HexToInteger(line24.get(2)) * 0.02;
        bmsData.batteryCellVoltage[24] = HexToInteger(line24.get(3)) * 0.02;
        bmsData.batteryCellVoltage[25] = HexToInteger(line24.get(4)) * 0.02;
        bmsData.batteryCellVoltage[26] = HexToInteger(line24.get(5)) * 0.02;
        bmsData.batteryCellVoltage[27] = HexToInteger(line24.get(6)) * 0.02;
        bmsData.batteryCellVoltage[28] = HexToInteger(line25.get(0)) * 0.02;
        bmsData.batteryCellVoltage[29] = HexToInteger(line25.get(1)) * 0.02;
        bmsData.batteryCellVoltage[30] = HexToInteger(line25.get(2)) * 0.02;
        bmsData.batteryCellVoltage[31] = HexToInteger(line25.get(3)) * 0.02;
        bmsData.batteryCellVoltage[32] = HexToInteger(line25.get(4)) * 0.02;
        bmsData.batteryCellVoltage[33] = HexToInteger(line25.get(5)) * 0.02;
        bmsData.batteryCellVoltage[34] = HexToInteger(line25.get(6)) * 0.02;
        bmsData.batteryCellVoltage[35]
        bmsData.batteryCellVoltage[36]
        bmsData.batteryCellVoltage[37]
        bmsData.batteryCellVoltage[38]
        bmsData.batteryCellVoltage[39]
        bmsData.batteryCellVoltage[40]
        bmsData.batteryCellVoltage[41]
        bmsData.batteryCellVoltage[42]
        bmsData.batteryCellVoltage[43]
        bmsData.batteryCellVoltage[44]
        bmsData.batteryCellVoltage[45]
        bmsData.batteryCellVoltage[46]
        bmsData.batteryCellVoltage[47]
        bmsData.batteryCellVoltage[48]
        bmsData.batteryCellVoltage[49]
        bmsData.batteryCellVoltage[50]
        bmsData.batteryCellVoltage[51]
        bmsData.batteryCellVoltage[52]
        bmsData.batteryCellVoltage[53]
        bmsData.batteryCellVoltage[54]
        bmsData.batteryCellVoltage[55]
        bmsData.batteryCellVoltage[56]
        bmsData.batteryCellVoltage[57]
        bmsData.batteryCellVoltage[58]
        bmsData.batteryCellVoltage[59]
        bmsData.batteryCellVoltage[60]
        bmsData.batteryCellVoltage[61]
        bmsData.batteryCellVoltage[62]
        bmsData.batteryCellVoltage[63]
        bmsData.batteryCellVoltage[64]
        bmsData.batteryCellVoltage[65]
        bmsData.batteryCellVoltage[66]
        bmsData.batteryCellVoltage[67]
        bmsData.batteryCellVoltage[68]
        bmsData.batteryCellVoltage[69]
        bmsData.batteryCellVoltage[70]
        bmsData.batteryCellVoltage[71]
        bmsData.batteryCellVoltage[72]
        bmsData.batteryCellVoltage[73]
        bmsData.batteryCellVoltage[74]
        bmsData.batteryCellVoltage[75]
        bmsData.batteryCellVoltage[76]
        bmsData.batteryCellVoltage[77]
        bmsData.batteryCellVoltage[78]
        bmsData.batteryCellVoltage[79]
        bmsData.batteryCellVoltage[80]
        bmsData.batteryCellVoltage[81]
        bmsData.batteryCellVoltage[82]
        bmsData.batteryCellVoltage[83]
        bmsData.batteryCellVoltage[84]
        bmsData.batteryCellVoltage[85]
        bmsData.batteryCellVoltage[86]
        bmsData.batteryCellVoltage[87]
        bmsData.batteryCellVoltage[88]
        bmsData.batteryCellVoltage[89]
        bmsData.batteryCellVoltage[90]
        bmsData.batteryCellVoltage[91]
        bmsData.batteryCellVoltage[92]
        bmsData.batteryCellVoltage[93]
        bmsData.batteryCellVoltage[94]
        bmsData.batteryCellVoltage[95]
     */

    private class ParsedRawData {
        private HashMap<String, ArrayList<String>> mMap = new HashMap<>();

        public ParsedRawData(String rawData) {
            // Split all strings on newlines
            final String [] lines = rawData.replaceAll("\\r", "").split("\\n");

            // Parse all lines
            for (String s: lines ) {
                // Split the line into items. We assume that:
                // - Headers are turned on (AT H1)
                // - Spaces are turned on (AT S1)

                // Remove all whitespace characters but ' '
                s = s.replaceAll("[\\t\\n\\x0B\\f\\r]", "");

                // Split the data into items
                String[] items = s.split("\\s");
                if ( items.length != 9 )
                {
                    // We should have 9 elements here : header + 8 data bytes
                    continue;
                }

                // Get the ECU ID
                final String ecuId = items[0];
                if( !ecuId.equals(BMS_ECU) )
                {
                    // The string is not from the BMS ECU, skip
                    continue;
                }

                // Get the first data byte
                final String byte0 = items[1];

                // Get the message data (7 bytes)
                ArrayList<String> messageData = new ArrayList<>();
                for( int i = 2; i < items.length; ++i ) {
                    // Only keep 2 bytes data values (remove "<DATA ERROR" values)
                    String item = items[i];
                    if ( item.length() == 2 ) {
                        messageData.add(items[i]);
                    }
                }

                // Add the data to the map
                mMap.put(byte0, messageData);
            }
        }

        boolean isValid() {
            return !mMap.isEmpty();
        }

        public ArrayList<String> getData(String key) {
            return mMap.get(key);
        }
    }

    public static int HexToInteger(String hex) {
        return Integer.parseInt(hex, 16);
    }

}
