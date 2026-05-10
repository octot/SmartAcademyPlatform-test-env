import { useState } from "react";
import { tutorSetupSteps } from "./tutorSetupSteps";

export default function TutorSetupWizard({
  formData,
  setFormData,
  onSubmit,
  submitting
}) {
  const [currentStep, setCurrentStep] = useState(0);

  const step = tutorSetupSteps[currentStep];
  const StepComponent = step.component;

  const isFirstStep = currentStep === 0;
  const isLastStep =
    currentStep === tutorSetupSteps.length - 1;

  const nextStep = () => {
    if (!isLastStep) {
      setCurrentStep((prev) => prev + 1);
    }
  };

  const previousStep = () => {
    if (!isFirstStep) {
      setCurrentStep((prev) => prev - 1);
    }
  };

  return (
    <div>
      <h2>{step.title}</h2>

      <StepComponent
        formData={formData}
        setFormData={setFormData}
      />

      <div>
        {!isFirstStep && (
          <button onClick={previousStep}>
            Previous
          </button>
        )}

        {!isLastStep ? (
          <button onClick={nextStep}>
            Next
          </button>
        ) : (
          <button
            onClick={onSubmit}
            disabled={submitting}
          >
            Submit
          </button>
        )}
      </div>
    </div>
  );
}