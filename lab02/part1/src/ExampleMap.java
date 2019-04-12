import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.Arrays;

class ExampleMap
{
    public static List<String> highEnrollmentStudents(
        Map<String, List<Course>> courseListsByStudentName, int unitThreshold)
    {
        List<String> overEnrolledStudents = new LinkedList<>();

        /*
            Build a list of the names of students currently enrolled
            in a number of units strictly greater than the unitThreshold.
        */
	Set<String> keygrabber = courseListsByStudentName.keySet();

	for (String keys : keygrabber){

		List<Course> ActualValues = courseListsByStudentName.get(keys); 

		int units = 0;
		for (Course val : ActualValues){
			units += val.getNumUnits();
		}
	if (units > unitThreshold){
		overEnrolledStudents.add(keys);
	}

	}
        return overEnrolledStudents;         
    }
}
