import {
  createForm,
  FieldValues,
  required,
  SubmitHandler,
} from "@modular-forms/solid";
import type { Component } from "solid-js";
import Button from "./components/Button";
import Card from "./components/Card";
import FieldText from "./components/FieldText";

const App: Component = () => {
  const [form, { Form, Field }] = createForm<ISignInForm>();

  const handleSubmit: SubmitHandler<ISignInForm> = (values, event) => {
    console.log("values", values);
    console.log("event", event);
  };

  return (
    <div class="min-h-screen flex items-center justify-center bg-base-300">
      <div class="mx-4 w-full md:w-1/3">
        <Card title="Sign In to your account">
          <Form onSubmit={handleSubmit}>
            <fieldset class="grid grid-cols-1 gap-4" disabled={form.submitting}>
              <Field
                name="username"
                validate={[required("Username is required!")]}>
                {(field, attrs) => (
                  <FieldText
                    label="Username"
                    field={field}
                    attrs={attrs}
                    required
                  />
                )}
              </Field>
              <Field
                name="password"
                validate={[required("Password is required!")]}>
                {(field, attrs) => (
                  <FieldText
                    label="Password"
                    field={field}
                    attrs={attrs}
                    required
                  />
                )}
              </Field>
            </fieldset>
            <div class="mt-4 flex justify-end gap-2">
              <Button label="Sign In" color="primary" type="submit" />
            </div>
          </Form>
        </Card>
      </div>
    </div>
  );
};

export default App;

interface ISignInForm extends FieldValues {
  username: string;
  password: string;
}
