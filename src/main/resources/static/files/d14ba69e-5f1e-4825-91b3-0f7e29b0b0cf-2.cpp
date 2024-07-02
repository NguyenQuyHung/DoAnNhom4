#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Student {
protected:
    string name;
    int score;
public:
    Student() {}
    Student(string name, int score) : name(name), score(score) {}
    virtual ~Student() {}

    void setName(string name) {
        this->name = name;
    }

    void setScore(int score) {
        this->score = score;
    }

    string getName() {
        return name;
    }

    int getScore() {
        return score;
    }

    virtual void DoAssignment() = 0;
    virtual void TakeTest() = 0;
    virtual void TakeExam() = 0;
};

class UniStudent : public Student {
private:
    int numSemesters;
    vector<vector<int>> courseAssignments;
    vector<int> courseTests;
    vector<int> courseExams;
public:
    UniStudent() {}
    UniStudent(string name, int score, int numSemesters)
            : Student(name, score), numSemesters(numSemesters) {
        courseAssignments.resize(numSemesters);
        courseTests.resize(numSemesters);
        courseExams.resize(numSemesters);
    }

    ~UniStudent() {}

    void DoAssignment() override {
        for (int sem = 0; sem < numSemesters; sem++) {
            for (auto& assignmentScore : courseAssignments[sem]) {
                assignmentScore = rand() % 101;
                score += assignmentScore;
            }
        }
    }

    void TakeTest() override {
        for (int sem = 0; sem < numSemesters; sem++) {
            courseTests[sem] = rand() % 101;
            score += courseTests[sem];
        }
    }

    void TakeExam() override {
        for (int sem = 0; sem < numSemesters; sem++) {
            courseExams[sem] = rand() % 101;
            score += courseExams[sem];
        }
    }
};

class CollegeStudent : public Student {
private:
    int numSemesters;
    vector<int> courseAssignments;
    vector<int> courseTests;
    vector<int> courseExams;
public:
    CollegeStudent() {}
    CollegeStudent(string name, int score, int numSemesters)
            : Student(name, score), numSemesters(numSemesters) {
        courseAssignments.resize(numSemesters);
        courseTests.resize(numSemesters);
        courseExams.resize(numSemesters);
    }

    ~CollegeStudent() {}

    void DoAssignment() override {
        for (int sem = 0; sem < numSemesters; sem++) {
            courseAssignments[sem] = rand() % 101;
            score += courseAssignments[sem];
        }
    }

    void TakeTest() override {
        for (int sem = 0; sem < numSemesters; sem++) {
            courseTests[sem] = rand() % 101;
            score += courseTests[sem];
        }
    }

    void TakeExam() override {
        for (int sem = 0; sem < numSemesters; sem++) {
            courseExams[sem] = rand() % 101;
            score += courseExams[sem];
        }
    }
};

class University {
private:
    string name;
    vector<Student*> students;
    static University* instance;
    
    University(string name) : name(name) {} // private constructor

public:
    static University* getInstance(string name) {
        if (!instance) {
            instance = new University(name);
        }
        return instance;
    }

    ~University() {}

    void addStudent(string name, int score, int numSemesters, bool isUniStudent) {
        Student* newStudent;
        if (isUniStudent) {
            newStudent = new UniStudent(name, score, numSemesters);
        } else {
            newStudent = new CollegeStudent(name, score, numSemesters);
        }
        students.push_back(newStudent);
        cout << "Student added successfully." << endl;
    }

    void display_Students() {
        cout << "List of all students in " << this->name << ":" << endl;
        for (const auto& student : students) {
            cout << student->getName() << " - " << student->getScore() << endl;
        }
    }

    void display_BestStudent() {
        int maxScore = students[0]->getScore();
        int numBestStudents = 0;
        for (const auto& student : students) {
            if (student->getScore() > maxScore) {
                maxScore = student->getScore();
                numBestStudents = 1;
            } else if (student->getScore() == maxScore) {
                numBestStudents++;
            }
        }
        cout << "Best student(s) in " << this->name << ":" << endl;
        for (const auto& student : students) {
            if (student->getScore() == maxScore) {
                cout << student->getName() << " - " << student->getScore() << endl;
            }
        }
        cout << "Number of best students: " << numBestStudents << endl;
    }

    void removeStudent(const string name) {
        for (auto it = students.begin(); it != students.end(); ++it) {
            if ((*it)->getName() == name) {
                delete *it;
                students.erase(it);
                cout << "Student removed successfully." << endl;
                return;
            }
        }
        cout << "Student not found." << endl;
    }

    void DoAssignment(const string name) {
        for (auto& student : students) {
            if (student->getName() == name) {
                student->DoAssignment();
                cout << "Assignments completed for student: " << name << endl;
                return;
            }
        }
        cout << "Student not found." << endl;
    }

    void TakeTest(const string name) {
        for (auto& student : students) {
            if (student->getName() == name) {
                student->TakeTest();
                cout << "Tests taken for student: " << name << endl;
                return;
            }
        }
        cout << "Student not found." << endl;
    }

    void TakeExam(const string name) {
        for (auto& student : students) {
            if (student->getName() == name) {
                student->TakeExam();
                cout << "Exam taken for student: " << name << endl;
                return;
            }
        }
        cout << "Student not found." << endl;
    }
};

University* University::instance = nullptr;

int main() {
    University* uni = University::getInstance("Bach Khoa University");

    int choice;

    while (true) {
        cout << "1. Add a new student" << endl;
        cout << "2. Display all students" << endl;
        cout << "3. Display best student(s)" << endl;
        cout << "4. Remove a student" << endl;
        cout << "5. Do Assignment" << endl;
        cout << "6. Take Test" << endl;
        cout << "7. Take Exam" << endl;
        cout << "8. Exit" << endl;
        cout << "Enter your choice: ";
        cin >> choice;

        switch (choice) {
            case 1: {
                string name;
                int score, numSemesters;
                int studentType;
                cout << "Enter the name of the student: ";
                cin.ignore();
                getline(cin, name);
                cout << "Enter the score of the student: ";
                cin >> score;
                cout << "Enter the number of semesters: ";
                cin >> numSemesters;
                cout << "Enter the type of student (1 for UniStudent, 2 for CollegeStudent): ";
                cin >> studentType;

                if (studentType == 1) {
                    uni->addStudent(name, score, numSemesters, true); // true: UniStudent
                } else if (studentType == 2) {
                    uni->addStudent(name, score, numSemesters, false); // false: CollegeStudent
                } else {
                    cout << "Invalid student type. Please enter 1 or 2." << endl;
                }
                break;
            }

            case 2: {
                uni->display_Students();
                break;
            }
            case 3: {
                uni->display_BestStudent();
                break;
            }
            case 4: {
                string name;
                cout << "Enter the name of the student to remove: ";
                cin.ignore();
                getline(cin, name);
                uni->removeStudent(name);
                break;
            }
            case 5: {
                string name;
                cout << "Enter the name of the student: ";
                cin.ignore();
                getline(cin, name);
                uni->DoAssignment(name);
                break;
            }
            case 6: {
                string name;
                cout << "Enter the name of the student: ";
                cin.ignore();
                getline(cin, name);
                uni->TakeTest(name);
                break;
            }
            case 7: {
                string name;
                cout << "Enter the name of the student: ";
                cin.ignore();
                getline(cin, name);
                uni->TakeExam(name);
                break;
            }
            case 8: {
                cout << "Exiting the program..." << endl;
                delete uni; // Release memory allocated for the singleton instance
                return 0;
            }
            default:
                cout << "Invalid choice. Please try again." << endl;
        }
    }
    return 0;
}

