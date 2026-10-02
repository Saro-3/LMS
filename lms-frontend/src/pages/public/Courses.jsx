import { useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
import {
  Search,
  BookOpen,
  Clock,
  BarChart3,
} from "lucide-react";

import api from "../../service/api";
import "./Courses.css";

function Courses() {
  const [courses, setCourses] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("All");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchCourses = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await api.get("/courses");

        console.log("Courses API Response:", response.data);

        setCourses(response.data);
      } catch (err) {
        console.error("Error fetching courses:", err);

        setError(
          "Unable to load courses. Please try again."
        );
      } finally {
        setLoading(false);
      }
    };

    fetchCourses();
  }, []);

  const categories = useMemo(() => {
    const categoryNames = courses
      .map((course) => course.category?.name)
      .filter(Boolean);

    return ["All", ...new Set(categoryNames)];
  }, [courses]);

  const filteredCourses = useMemo(() => {
    return courses.filter((course) => {
      const title = course.title || "";
      const description = course.description || "";
      const categoryName = course.category?.name || "";

      const search = searchTerm.toLowerCase();

      const matchesSearch =
        title.toLowerCase().includes(search) ||
        description.toLowerCase().includes(search);

      const matchesCategory =
        selectedCategory === "All" ||
        categoryName === selectedCategory;

      return matchesSearch && matchesCategory;
    });
  }, [courses, searchTerm, selectedCategory]);

  return (
    <section className="courses-page">

      {/* Hero */}
      <div className="courses-hero">
        <div className="courses-hero-content">

          <span className="courses-badge">
            Explore Our Courses
          </span>

          <h1>
            Learn Skills That Build Your Future
          </h1>

          <p>
            Explore practical courses designed to help you
            develop real-world technical skills.
          </p>

        </div>
      </div>

      {/* Main Content */}
      <div className="courses-container">

        {/* Search */}
        <div className="courses-toolbar">

          <div className="courses-search">

            <Search size={19} />

            <input
              type="text"
              placeholder="Search courses..."
              value={searchTerm}
              onChange={(e) =>
                setSearchTerm(e.target.value)
              }
            />

          </div>

        </div>

        {/* Category Filters */}
        <div className="courses-filters">

          {categories.map((category) => (
            <button
              key={category}
              className={
                selectedCategory === category
                  ? "course-filter active"
                  : "course-filter"
              }
              onClick={() =>
                setSelectedCategory(category)
              }
            >
              {category}
            </button>
          ))}

        </div>

        {/* Loading */}
        {loading && (
          <div className="courses-message">
            <p>Loading courses...</p>
          </div>
        )}

        {/* Error */}
        {!loading && error && (
          <div className="courses-message error">
            <p>{error}</p>
          </div>
        )}

        {/* Courses */}
        {!loading && !error && (
          <>
            {filteredCourses.length > 0 ? (

              <div className="courses-grid">

                {filteredCourses.map((course) => (

                  <article
                    className="course-card"
                    key={course.id}
                  >

                    {/* Course Image */}
                    <div className="course-card-image">

                      {course.thumbnailUrl ? (

                        <img
                          src={course.thumbnailUrl}
                          alt={course.title}
                        />

                      ) : (

                        <BookOpen size={42} />

                      )}

                    </div>

                    {/* Course Content */}
                    <div className="course-card-content">

                      <span className="course-category">
                        {course.category?.name || "Course"}
                      </span>

                      <h2>
                        {course.title}
                      </h2>

                      <p>
                        {course.description ||
                          "Learn practical skills through this course."}
                      </p>

                      <div className="course-meta">

                        <span>
                          <BarChart3 size={16} />

                          {course.level || "Beginner"}
                        </span>

                        <span>
                          <Clock size={16} />

                          {course.durationHours
                            ? `${course.durationHours} Hours`
                            : "Self-paced"}
                        </span>

                      </div>

                      <Link
                        to={`/courses/${course.id}`}
                        className="course-view-button"
                      >
                        View Course
                      </Link>

                    </div>

                  </article>

                ))}

              </div>

            ) : (

              <div className="courses-message">
                <p>
                  No courses found.
                </p>
              </div>

            )}
          </>
        )}

      </div>

    </section>
  );
}

export default Courses;