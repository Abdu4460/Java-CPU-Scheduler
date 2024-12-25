import React from "react";
import { Card, Button, Box, Typography } from "@mui/material";
import * as Yup from "yup";
import { List, ListItem } from "@mui/material";

// Form validation schema using Yup
const validationSchema = Yup.object({
  firstName: Yup.string().required("This is a required field"),
  lastName: Yup.string().required("This is a required field"),
});

const StepOne = ({ nextStep }) => {
  return (
    <Box mt={10} display="flex" justifyContent="center">
      <Card variant="outlined" sx={{ maxWidth: 500, p: 3 }}>
        <Typography variant="h5" mb={2}>
          CPU Task Scheduling Simulator
        </Typography>
        <Typography>
          Welcome! This project is an implementation of 4 process-scheduling
          algorithms done from the{" "}
          <a
            href="https://www.mbit.edu.in/wp-content/uploads/2020/05/Operating_System_Concepts_8th_EditionA4.pdf"
            target="_blank"
            rel="noreferrer"
          >
            Operating Systems Concepts book (10th Edition) by Silberschatz et
            al.
          </a>{" "}
          The four algorithms (all non-preemptive) are:
          <List sx={{ listStyleType: "disc", marginLeft: "60px" }}>
            <ListItem sx={{ display: "list-item" }}>
              First-Come-First-Serve
            </ListItem>
            <ListItem sx={{ display: "list-item" }}>
              Shortest Job First
            </ListItem>
            <ListItem sx={{ display: "list-item" }}>
              Priority Scheduling
            </ListItem>
            <ListItem sx={{ display: "list-item" }}>
              Round-Robin Scheduling
            </ListItem>
          </List>
          This app allows you to interact with the task scheduling simulator by
          selecting which algorithm you would like to test, adding in the task
          information you want to schedule, and specifying any special
          parameters depending on the selected algorithm. I hope you like this
          project!
        </Typography>
        <Button
          sx={{ flexDirection: "row", marginTop: "20px" }}
          type="button"
          variant="contained"
          color="primary"
          onClick={nextStep}
        >
          Get Started
        </Button>
      </Card>
    </Box>
  );
};

export default StepOne;
