import { useState } from "react";

import { studentSetupSteps }
    from "./studentSetupSteps";

import "./StudentSetupWizard.css";

export default function StudentSetupWizard({
    formData,
    setFormData,
    onSubmit,
    submitting
}) {

    const [currentStep] = useState(0);

    const step =
        studentSetupSteps[currentStep];

    const StepComponent = step.component;

    return (
        <div className="wizard-container">

            <div className="wizard-card">

                {/* Header */}
                <div className="wizard-header">

                    <div className="step-indicator">
                        Step 1 of 1
                    </div>

                    <h1 className="wizard-title">
                        {step.title}
                    </h1>

                    <p className="wizard-subtitle">
                        Complete your student
                        profile setup.
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

                    <div />

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

                </div>

            </div>

        </div>
    );
}