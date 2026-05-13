import { useState } from "react";
import { tutorSetupSteps } from "./tutorSetupSteps";

import "./TutorSetupWizard.css";

export default function TutorSetupWizard({
  formData,
  setFormData,
  onSubmit,
  submitting
}) {

  const [currentStep, setCurrentStep] =
    useState(0);

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
    <div className="wizard-container">

      <div className="wizard-card">

        {/* Header */}
        <div className="wizard-header">

          <div className="step-indicator">
            Step {currentStep + 1} of{" "}
            {tutorSetupSteps.length}
          </div>

          <h1 className="wizard-title">
            {step.title}
          </h1>

          <p className="wizard-subtitle">
            Complete your tutor profile setup
          </p>

        </div>

        {/* Step Content */}
        <div className="step-content">

          <StepComponent
            formData={formData}
            setFormData={setFormData}
          />

        </div>

        {/* Footer */}
        <div className="wizard-footer">

          <div>
            {!isFirstStep && (
              <button
                className="
                                    wizard-button
                                    secondary
                                "
                onClick={previousStep}
              >
                Previous
              </button>
            )}
          </div>

          <div>
            {!isLastStep ? (
              <button
                className="
                                    wizard-button
                                    primary
                                "
                onClick={nextStep}
              >
                Next
              </button>
            ) : (
              <button
                className="
                                    wizard-button
                                    primary
                                "
                onClick={onSubmit}
                disabled={submitting}
              >
                {submitting
                  ? "Submitting..."
                  : "Complete Setup"}
              </button>
            )}
          </div>

        </div>

      </div>

    </div>
  );
}