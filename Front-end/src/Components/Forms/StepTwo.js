import React from "react";
import {
  Card,
  TextField,
  Button,
  Box,
  Typography,
  MenuItem,
  Checkbox,
  FormControlLabel,
  Radio,
  FormLabel,
  RadioGroup,
} from "@mui/material";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import _ from "lodash";

// Validation schema
const validationSchema = Yup.object({
  algorithmName: Yup.string().required("Algorithm name is required"),

  priority: Yup.boolean().notRequired(),

  quantum: Yup.number().when("algorithmName", {
    is: "RR", // Round-Robin
    then: () =>
      Yup.number()
        .required("Quantum time is required")
        .positive("Quantum must be positive")
        .integer("Quantum must be an integer"),
    otherwise: () => Yup.number().notRequired(),
  }),

  tasks: Yup.array()
    .of(
      Yup.object({
        name: Yup.string().required("Task name is required"),
        priority: Yup.number()
          .min(0, "Priority level must be 0 or higher")
          .required("Priority level is required"),
        burst: Yup.number()
          .min(0, "Burst time must be 0 or higher")
          .required("Burst time is required"),
        arrivalTime: Yup.number()
          .min(0, "Arrival time must be 0 or higher")
          .required("Arrival time is required"),
      })
    )
    .min(1, "At least one task is required"),
});

const StepTwo = ({ nextStep, handleFormData, prevStep, values }) => {
  const onSubmit = (formValues) => {
    handleFormData("algorithmName")({
      target: { value: formValues.algorithmName },
    });
    handleFormData("priority")({ target: { value: formValues.priority } });
    handleFormData("quantum")({ target: { value: formValues.quantum } });
    handleFormData("tasks")({ target: { value: formValues.tasks } });
    nextStep();
  };

  const taskTemplate = (index) => ({
    name: `Task ${index + 1}`,
    priority: 0,
    burst: 0,
    arrivalTime: 0,
  });

  return (
    <Box mt={10} display="flex" justifyContent="center">
      <Card variant="outlined" sx={{ maxWidth: 500, p: 3, boxShadow: 3, }}>
        <Typography variant="h5" mb={2}>
          Enter Task Details
        </Typography>
        <Formik
          initialValues={{
            algorithmName: values.algorithmName || "",
            priority: values.priority || false,
            quantum: values.quantum || 0,
            tasks: values.tasks || [taskTemplate(0)],
          }}
          validationSchema={validationSchema}
          onSubmit={onSubmit}
        >
          {({ values, errors, touched, handleChange, setFieldValue }) => (
            <Form>
              {/* Algorithm Name */}
              <Box mb={2}>
                <TextField
                  select
                  name="algorithmName"
                  label="Algorithm Name"
                  fullWidth
                  value={values.algorithmName}
                  onChange={handleChange}
                  error={touched.algorithmName && !!errors.algorithmName}
                  helperText={<ErrorMessage name="algorithmName" />}
                >
                  <MenuItem value="FCFS">First-Come-First-Serve</MenuItem>
                  <MenuItem value="SJF">Shortest Job First</MenuItem>
                  <MenuItem value="PS">Priority Scheduling</MenuItem>
                  <MenuItem value="RR">Round-Robin Scheduling</MenuItem>
                </TextField>
              </Box>

              {/* Priority Checkbox */}
              <Box
                mb={2}
                sx={{
                  backgroundColor:
                    values.algorithmName === "PS" ? "grey.100" : "grey.300", // Change color based on condition
                  opacity: values.algorithmName === "PS" ? 1 : 0.5, // Grey out visually
                  p: 2,
                  borderRadius: 1,
                }}
              >
                <FormLabel sx={{ mb: 1, display: "block" }}>
                  Priority Order (for Priority Scheduling only)
                </FormLabel>
                <RadioGroup
                  sx={{ display: "inline-block" }}
                  defaultValue="ascending"
                  name="radio-buttons-group"
                >
                  <FormControlLabel
                    value="ascending"
                    control={
                      <Radio
                        disabled={values.algorithmName !== "PS"} // Disable interaction conditionally
                      />
                    }
                    label="Ascending"
                  />
                  <FormControlLabel
                    value="descending"
                    control={
                      <Radio
                        disabled={values.algorithmName !== "PS"} // Disable interaction conditionally
                      />
                    }
                    label="Descending"
                  />
                </RadioGroup>
              </Box>

              {/* Quantum */}
              <Box
                mb={2}
                sx={{
                  backgroundColor:
                    values.algorithmName === "RR" ? "grey.100" : "grey.300", // Change color based on condition
                  opacity: values.algorithmName === "RR" ? 1 : 0.5, // Grey out visually
                  p: 2,
                  borderRadius: 1,
                }}
              >
                <FormLabel sx={{ display: "block", mb: 2 }}>
                  Quantum Time (for Round-Robin Scheduling only)
                </FormLabel>
                <Field
                  as={TextField}
                  name="quantum"
                  type="number"
                  label="Quantum Time"
                  disabled={values.algorithmName !== "RR"}
                  fullWidth
                  error={touched.quantum && !!errors.quantum}
                  helperText={<ErrorMessage name="quantum" />}
                />
              </Box>

              <Box mb={2}>
                {/* Placeholder or Task Inputs */}
                {values.tasks.length === 0 ? (
                  <Box
                    display="flex"
                    justifyContent="center"
                    alignItems="center"
                    height="100px"
                    border="1px dashed grey"
                    borderRadius="4px"
                    sx={{ backgroundColor: "grey.100", textAlign: "center" }}
                  >
                    <Typography variant="body1" color="textSecondary">
                      No tasks added yet. Click the "Add Task" button to add some now.
                    </Typography>
                  </Box>
                ) : (
                  values.tasks.map((task, index) => (
                    <Box key={index} display="flex" flexDirection="row" mb={2}>
                      <Typography
                        sx={{
                          mr: 2,
                          alignSelf: "center",
                          display: "inline-flex",
                        }}
                      >
                        {values.tasks[index].name}
                      </Typography>
                      <TextField
                        sx={{ margin: "20px" }}
                        name={`tasks[${index}].priority`}
                        value={task.priority}
                        onChange={(e) =>
                          setFieldValue(
                            `tasks[${index}].priority`,
                            Math.max(0, e.target.value)
                          )
                        }
                        label="Priority Level"
                        type="number"
                        fullWidth
                        error={
                          touched.tasks?.[index]?.priority &&
                          !!errors.tasks?.[index]?.priority
                        }
                        helperText={errors.tasks?.[index]?.priority || ""}
                      />
                      <TextField
                        sx={{ margin: "20px" }}
                        name={`tasks[${index}].burst`}
                        value={task.burst}
                        onChange={(e) =>
                          setFieldValue(
                            `tasks[${index}].burst`,
                            Math.max(0, e.target.value)
                          )
                        }
                        label="Burst Time"
                        type="number"
                        fullWidth
                        error={
                          touched.tasks?.[index]?.burst &&
                          !!errors.tasks?.[index]?.burst
                        }
                        helperText={errors.tasks?.[index]?.burst || ""}
                      />
                      <TextField
                        sx={{ margin: "20px" }}
                        name={`tasks[${index}].arrivalTime`}
                        value={task.arrivalTime}
                        onChange={(e) =>
                          setFieldValue(
                            `tasks[${index}].arrivalTime`,
                            Math.max(0, e.target.value)
                          )
                        }
                        label="Arrival Time"
                        type="number"
                        fullWidth
                        error={
                          touched.tasks?.[index]?.arrivalTime &&
                          !!errors.tasks?.[index]?.arrivalTime
                        }
                        helperText={errors.tasks?.[index]?.arrivalTime || ""}
                      />
                      <Button
                        variant="outlined"
                        color="secondary"
                        sx={{ mt: 1 }}
                        onClick={() =>
                          setFieldValue(
                            "tasks",
                            values.tasks.filter((_, i) => i !== index)
                          )
                        }
                      >
                        Remove
                      </Button>
                    </Box>
                  ))
                )}

                {/* Add Task Button */}
                <Button
                  variant="outlined"
                  color="primary"
                  onClick={() =>
                    setFieldValue("tasks", [
                      ...values.tasks,
                      taskTemplate(values.tasks.length),
                    ])
                  }
                  sx={{ mt: 2 }}
                >
                  Add Task
                </Button>

                {/* Validation Error */}
                {errors.tasks && (
                  <Typography color="error" variant="body2" mt={1}>
                    {errors.tasks}
                  </Typography>
                )}
              </Box>


              {/* Buttons */}
              <Box display="flex" justifyContent="space-between" mt={3}>
                <Button variant="outlined" color="primary" onClick={prevStep}>
                  Previous
                </Button>
                <Button type="submit" variant="contained" color="primary">
                  Submit
                </Button>
              </Box>
            </Form>
          )}
        </Formik>
      </Card>
    </Box>
  );
};

export default StepTwo;
