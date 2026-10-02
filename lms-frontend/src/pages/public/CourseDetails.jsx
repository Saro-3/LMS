import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { ArrowLeft, BookOpen, Clock, User, CheckCircle, PlayCircle } from "lucide-react";
import toast from "react-hot-toast";

import api from "../../service/api";
import { useAuth } from "../../context/AuthContext";

import "./CourseDetails.css";

function CourseDetails() {
  const { courseId } = useParams();
  const navigate = useNavigate();
  const { user, isAuthenticated } = useAuth();

  const [course, setCourse] = useState(null);
  const [modules, setModules] = useState([]);
  const [enrollment, setEnrollment] = useState(null);

  const [loading, setLoading] = useState(true);
  const [enrolling, setEnrolling] = useState(false);

  useEffect(() => {
    fetchCourseDetails();
  }, [courseId]);

  useEffect(() => {
    if (
      isAuthenticated &&
      user?.role === "ROLE_STUDENT" &&
      user?.userId
    ) {
      checkEnrollment();
    }
  }, [courseId, isAuthenticated, user]);

  const fetchCourseDetails = async () => {
    try {
      setLoading(true);

      const [courseResponse, modulesResponse] = await Promise.all([
        api.get(`/courses/${courseId}`),
        api.get(`/courses/${courseId}/modules`),
      ]);

      setCourse(courseResponse.data);
      setModules(modulesResponse.data || []);
    } catch (error) {
      console.error("Failed to load course details:", error);

      toast.error(
        error.response?.data?.message ||
          "Failed to load course details"
      );
    } finally {
      setLoading(false);
    }
  };

  const checkEnrollment = async () => {
    try {
      const response = await api.get(
        `/enrollments/student/${user.userId}`
      );

      const enrollments = response.data || [];

      const existingEnrollment = enrollments.find(
        (item) =>
          Number(item.course?.id) === Number(courseId)
      );

      setEnrollment(existingEnrollment || null);
    } catch (error) {
      console.error("Failed to check enrollment:", error);
    }
  };

  const handleEnroll = async () => {
    if (!isAuthenticated) {
      toast.error("Please login to enroll in this course.");
      navigate("/login", {
        state: {
          from: `/courses/${courseId}`,
        },
      });
      return;
    }

    if (user?.role !== "ROLE_STUDENT") {
      toast.error("Only students can enroll in courses.");
      return;
    }

    if (!user?.userId) {
      toast.error("User information is missing. Please login again.");
      return;
    }

    try {
      setEnrolling(true);

      const response = await api.post(
        `/enrollments`,
        null,
        {
          params: {
            studentId: user.userId,
            courseId: courseId,
          },
        }
      );

      setEnrollment(response.data);

      toast.success("Successfully enrolled in the course!");
    } catch (error) {
      console.error("Enrollment failed:", error);

      const message =
        error.response?.data?.message ||
        error.response?.data?.error ||
        "Failed to enroll in the course.";

      toast.error(message);
    } finally {
      setEnrolling(false);
    }
  };

  const handleContinueLearning = () => {
    if (!enrollment) return;

    toast.success("Course learning module coming next.");

    // Student learning page will be connected later.
    // navigate(`/student/courses/${courseId}/learn`);
  };

  if (loading) {
    return (
      <div className="course-details-page">
        <div className="course-details-container">
          <div className="course-details-loading">
            <div className="loading-spinner"></div>
            <p>Loading course details...</p>
          </div>
        </div>
      </div>
    );
  }

  if (!course) {
    return (
      <div className="course-details-page">
        <div className="course-details-container">
          <div className="course-not-found">
            <BookOpen size={48} />
            <h2>Course Not Found</h2>
            <p>
              The course you're looking for could not be found.
            </p>

            <Link to="/courses" className="back-courses-btn">
              <ArrowLeft size={18} />
              Back to Courses
            </Link>
          </div>
        </div>
      </div>
    );
  }

  const categoryName =
    course.category?.name || "General";

  const instructorName =
    course.instructor?.name || "LMS Instructor";

  const thumbnail =
    course.thumbnailUrl ||
    "https://via.placeholder.com/1200x650?text=Course";

  const isPublished =
    course.status === "PUBLISHED";

  const isStudent =
    user?.role === "ROLE_STUDENT";

  return (
    <div className="course-details-page">

      {/* Hero Section */}
      <section className="course-details-hero">
        <div className="course-details-container">

          <Link to="/courses" className="course-back-link">
            <ArrowLeft size={18} />
            Back to Courses
          </Link>

          <div className="course-details-hero-grid">

            {/* Course Image */}
            <div className="course-details-image-wrapper">
              <img
                src={thumbnail}
                alt={course.title}
                className="course-details-image"
              />
            </div>

            {/* Course Main Information */}
            <div className="course-details-main">

              <span className="course-category-badge">
                {categoryName}
              </span>

              <h1>{course.title}</h1>

              <p className="course-details-description">
                {course.description}
              </p>

              <div className="course-meta">

                <div className="course-meta-item">
                  <User size={18} />
                  <div>
                    <span>Instructor</span>
                    <strong>{instructorName}</strong>
                  </div>
                </div>

                <div className="course-meta-item">
                  <Clock size={18} />
                  <div>
                    <span>Duration</span>
                    <strong>
                      {course.durationHours || 0} Hours
                    </strong>
                  </div>
                </div>

                <div className="course-meta-item">
                  <BookOpen size={18} />
                  <div>
                    <span>Level</span>
                    <strong>
                      {course.level || "All Levels"}
                    </strong>
                  </div>
                </div>

              </div>

              {/* Enrollment Area */}
              <div className="course-enrollment-area">

                {enrollment ? (
                  <div className="enrolled-section">

                    <div className="enrolled-message">
                      <CheckCircle size={22} />
                      <div>
                        <strong>You're enrolled!</strong>
                        <span>
                          You can continue learning this course.
                        </span>
                      </div>
                    </div>

                    <button
                      className="continue-learning-btn"
                      onClick={handleContinueLearning}
                    >
                      <PlayCircle size={20} />
                      Continue Learning
                    </button>

                  </div>
                ) : isStudent ? (
                  <button
                    className="enroll-btn"
                    onClick={handleEnroll}
                    disabled={enrolling || !isPublished}
                  >
                    {enrolling ? (
                      <>
                        <span className="button-spinner"></span>
                        Enrolling...
                      </>
                    ) : !isPublished ? (
                      "Course Not Available"
                    ) : (
                      <>
                        <BookOpen size={20} />
                        Enroll Now
                      </>
                    )}
                  </button>
                ) : isAuthenticated ? (
                  <div className="login-enrollment-message">
                    <p>
                      Only students can enroll in courses.
                    </p>
                  </div>
                ) : (
                  <Link
                    to="/login"
                    state={{
                      from: `/courses/${courseId}`,
                    }}
                    className="enroll-btn"
                  >
                    <BookOpen size={20} />
                    Login to Enroll
                  </Link>
                )}

              </div>

            </div>
          </div>
        </div>
      </section>

      {/* Course Content */}
      <section className="course-content-section">
        <div className="course-details-container">

          <div className="course-content-header">
            <div>
              <span className="section-label">
                COURSE CONTENT
              </span>

              <h2>What You'll Learn</h2>

              <p>
                Explore the modules and lessons included
                in this course.
              </p>
            </div>

            <div className="module-count">
              <BookOpen size={18} />
              {modules.length}{" "}
              {modules.length === 1 ? "Module" : "Modules"}
            </div>
          </div>

          {modules.length === 0 ? (
            <div className="empty-modules">
              <BookOpen size={42} />
              <h3>Course content coming soon</h3>
              <p>
                The instructor hasn't added modules yet.
              </p>
            </div>
          ) : (
            <div className="modules-list">

              {modules.map((module, index) => (
                <div
                  className="course-module-card"
                  key={module.id}
                >

                  <div className="module-number">
                    {index + 1}
                  </div>

                  <div className="module-information">

                    <h3>
                      {module.title}
                    </h3>

                    {module.description && (
                      <p>
                        {module.description}
                      </p>
                    )}

                    <div className="module-lessons-info">
                      <BookOpen size={16} />

                      {module.lessons?.length !== undefined
                        ? `${module.lessons.length} ${
                            module.lessons.length === 1
                              ? "Lesson"
                              : "Lessons"
                          }`
                        : "Lessons"}
                    </div>

                  </div>

                </div>
              ))}

            </div>
          )}

        </div>
      </section>

      {/* Bottom CTA */}
      <section className="course-bottom-cta">
        <div className="course-details-container">

          <div>
            <span>READY TO START LEARNING?</span>
            <h2>
              Start your learning journey today.
            </h2>
          </div>

          {enrollment ? (
            <button
              className="cta-button"
              onClick={handleContinueLearning}
            >
              Continue Learning
              <PlayCircle size={19} />
            </button>
          ) : isStudent ? (
            <button
              className="cta-button"
              onClick={handleEnroll}
              disabled={enrolling || !isPublished}
            >
              {enrolling ? "Enrolling..." : "Enroll Now"}
              <BookOpen size={19} />
            </button>
          ) : (
            <Link
              to="/login"
              state={{
                from: `/courses/${courseId}`,
              }}
              className="cta-button"
            >
              Login to Start
              <BookOpen size={19} />
            </Link>
          )}

        </div>
      </section>

    </div>
  );
}

export default CourseDetails;