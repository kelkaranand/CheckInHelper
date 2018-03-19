import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

public class Console {

	static List<CheckIn> checkInRecords = new ArrayList<CheckIn>();
	static List<Student> studentRecords = new ArrayList<Student>();
	static String eventName = "";
	static boolean debugMode = true;
	static Encrypter encrypter=new Encrypter();
	static boolean encryption =true;

	/**
	 * Write Student data from eventdata json file
	 */
	public static void pushStudentData(String event) {
		clearLocalStudentList();
		readStudentList();
		createStudentJSON();
		pushStudentDataToFirebase(event);
	}
	
	public static void clearLocalStudentList()
	{
		studentRecords= new ArrayList<Student>();
	}

	/**
	 * Function to read data from studentList.csv and add to studentRecords
	 * object
	 */
	public static void readStudentList() {
		log("Reading from Student data");
		encrypter.initialize();
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					"studentList.csv"));
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] fragments = line.split(",");
				if (!fragments[0].equals("APS ID")) {
					Student record;
					if(encryption){
						
					record = new Student(fragments[0], encrypter.encrypt(fragments[1]),
							encrypter.encrypt(fragments[2]), encrypter.encrypt(fragments[3]), fragments[4], fragments[5]);
					}
					else{
					record = new Student(fragments[0], fragments[1],
								fragments[2], fragments[3], fragments[4], fragments[5]);
					}

					log(fragments[0] + "|" + fragments[1] + "|" + fragments[2]
							+ "|" + fragments[3] + "|" + fragments[4]+ "|" + fragments[5]);
					studentRecords.add(record);
				}
			}
			br.close();
		} catch (Exception e) {
			log(e.getMessage());
		}
	}

	/**
	 * Function to generate EventData json
	 */
	public static void createStudentJSON() {
		// String jsonData =
		// "{\"CheckIn\":[{\"id\":\"000\",\"media\":\"Y\",\"guests\":\"0\"}],\"Event\":{\"name\":\""
		// + event + "\"}, \"students\":[";

		// String jsonData = "{\"students\":[";
		String jsonData = "{";

		int counter = 0;
		for (Student record : studentRecords) {
			jsonData = jsonData + "\"" + (counter++) + "\":" + "{\"id\":\""
					+ record.id + "\",\"fname\":\"" + record.fname
					+ "\",\"lname\":\"" + record.lname + "\",\"school\":\""
					+ record.sname + "\",\"media\":\"" + record.media + "\",\"vip\":\""+record.vip+"\"},";
		}
		jsonData = jsonData.substring(0, jsonData.length() - 1);
		// jsonData = jsonData + "]}";
		jsonData = jsonData + "}";

		JSONObject json = new JSONObject(jsonData);
		log(json.toString());
		try {
			FileWriter pw = new FileWriter(new File("EventData.json"));
			StringBuilder sb = new StringBuilder();
			sb.append(json.toString());
			pw.write(sb.toString());
			pw.close();
		} catch (Exception e) {
			log(e.getMessage());
		}
	}

	/**
	 * Function to write student data to firebase
	 */
	public static void pushStudentDataToFirebase(String event) {
		log("Writing Student data to firebase");
		try {

			BufferedReader br = new BufferedReader(new FileReader(
					"EventData.json"));
			String data = br.readLine();
			br.close();
			log(data);

			URL url = new URL(
					"https://checkin-e07f4.firebaseio.com/students.json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT");
			OutputStreamWriter out = new OutputStreamWriter(
					conn.getOutputStream());
			out.write(data);
			out.close();
			log(conn.getResponseCode());

			String eventData = "{\"name\":\"" + event + "\"}";
			url = new URL("https://checkin-e07f4.firebaseio.com/Event.json");
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT");
			out = new OutputStreamWriter(conn.getOutputStream());
			out.write(eventData);
			out.close();
			log(conn.getResponseCode());

		} catch (Exception e) {
			log(e.getMessage());
		}
	}

	/**
	 * Function to generate check in csv
	 */
	public static void generateCheckInCSV() {
		readEventName();
		readCheckIns();
	}

	/**
	 * Function to read event name from firebase
	 */
	public static void readEventName() {
		log("Reading Event Name");
		try {
			URL url = new URL("https://checkin-e07f4.firebaseio.com/Event.json");
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(url.openStream(), "UTF-8"))) {
				for (String line; (line = reader.readLine()) != null;) {
					JSONObject json = new JSONObject(line);
					Iterator<String> keys = json.keys();
					while (keys.hasNext()) {
						eventName = json.get(keys.next()).toString();
						log("Event Name: " + eventName);
					}

				}
			}
		} catch (Exception e) {
			log(e.getMessage());
		}
	}

	/**
	 * Function to clear data from firebase
	 */
	public static void clearFirebase() {
		clearStudents();
		clearCheckIns();
		clearEvent();
	}

	/**
	 * Function to clear student data from firbase
	 */
	public static void clearStudents() {
		log("Clearing Student data from firebase");
		try {
			URL url = new URL(
					"https://checkin-e07f4.firebaseio.com/students.json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("DELETE");
			log(conn.getResponseCode());
		} catch (Exception e) {
			log(e.getMessage());
		}
	}

	/**
	 * Functoin to clear check in data from firebase
	 */
	public static void clearCheckIns() {
		log("Clearing Student data from firebase");
		try {
			URL url = new URL(
					"https://checkin-e07f4.firebaseio.com/CheckIn.json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("DELETE");
			log(conn.getResponseCode());

		} catch (Exception e) {
			log(e.getMessage());
		}
	}
	
	/**
	 * Functoin to clear check in data from firebase
	 */
	public static void clearEvent() {
		log("Clearing Event details from firebase");
		try {
			URL url = new URL(
					"https://checkin-e07f4.firebaseio.com/Event.json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("DELETE");
			log(conn.getResponseCode());

		} catch (Exception e) {
			log(e.getMessage());
		}
	}

	/**
	 * Function to read check in data from firebase
	 */
	public static void readCheckIns() {
		log("Reading Check In data");
		try {
			URL url = new URL(
					"https://checkin-e07f4.firebaseio.com/CheckIn.json");
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(url.openStream(), "UTF-8"))) {
				for (String line; (line = reader.readLine()) != null;) {
					JSONObject json = new JSONObject(line);
					Iterator<String> keys = json.keys();
					while (keys.hasNext()) {

						String temp = json.get(keys.next()).toString();
						formatObject(new JSONObject(temp));
					}

				}
				removeDuplicates();
				if (debugMode) {
					printCheckIns();
				}
				createCheckInCSV();
			}
		} catch (Exception e) {
			log(e.getMessage());
		}
	}

	/**
	 * Function to format the firebase check in record into custom object
	 * 
	 * @param json
	 *            JSONObject
	 */
	public static void formatObject(JSONObject json) {
		String id = json.getString("id");
		String guests = json.getString("guests");
		String media = json.getString("media");

		// Convert media indicator to true/false
		if (media.equals("Y")) {
			media = "true";
		} else if (media.equals("N")) {
			media = "false";
		}

		log("Formating record with id: " + id);
		if (!id.equals("000")) {
			CheckIn record = new CheckIn(id, guests, media);
			checkInRecords.add(record);
			log("Added record with id: " + id);
		} else {
			log("Dummy record not added");
		}
	}

	/**
	 * Function used to remove duplicate check in records
	 */
	public static void removeDuplicates() {
		log("--- Removing duplicate records ---");
		HashMap<String, CheckIn> map = new HashMap<String, CheckIn>();
		List<CheckIn> tempCheckInRecords = new ArrayList<CheckIn>();
		for (int i = 0; i < checkInRecords.size(); i++) {
			CheckIn record = checkInRecords.get(i);
			if (!map.containsKey(record.id)) {
				tempCheckInRecords.add(record);
				map.put(record.id, record);
			} else {
				log("Found duplicate for id: " + record.id);
			}
		}
		checkInRecords = tempCheckInRecords;
	}

	/**
	 * Function used to print processed check in records to console
	 */
	public static void printCheckIns() {
		log("Processed Check In Records");
		for (CheckIn record : checkInRecords) {
			System.out.println("id: " + record.id);
			System.out.println("guests: " + record.guests);
			System.out.println("media: " + record.media);
			System.out.println();
		}
	}

	/**
	 * Function to create a file name for the check in csv using the event name
	 * 
	 * @return String
	 */
	public static String formatCheckInFileName() {
		String temp = "";
		String[] fragments = eventName.split(" ");
		for (String fragment : fragments) {
			temp = temp + fragment;
		}
		temp = temp + ".csv";
		log(temp);
		return temp;

	}

	/**
	 * Function to generate check in csv
	 * 
	 * @throws FileNotFoundException
	 */
	public static void createCheckInCSV() {
		try {
			PrintWriter pw = new PrintWriter(new File(formatCheckInFileName()));
			StringBuilder sb = new StringBuilder();
			// Generate file headers
			sb.append("Event Name");
			sb.append(',');
			sb.append("APS ID");
			sb.append(',');
			sb.append("Guests");
			sb.append(',');
			sb.append("Media");
			sb.append('\n');

			// Add data
			for (CheckIn record : checkInRecords) {
				sb.append(eventName);
				sb.append(',');
				sb.append(record.id);
				sb.append(',');
				sb.append(record.guests);
				sb.append(',');
				sb.append(record.media);
				sb.append('\n');
			}

			pw.write(sb.toString());
			pw.close();

			log("Check In file created for " + eventName);
		} catch (Exception e) {
			log(e.getMessage());
		}
	}

	/**
	 * Function to print messages to console in DEBUG mode
	 * 
	 * @param message
	 *            String
	 */
	public static void log(Object message) {
		if (debugMode) {
			System.out.println(message.toString());
		}
	}

}