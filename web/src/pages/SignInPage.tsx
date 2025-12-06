import Button from "@/components/Button";
import Card from "@/components/Card";
import FieldText from "@/components/FieldText";
import {
  createForm,
  FieldValues,
  required,
  SubmitHandler,
} from "@modular-forms/solid";
import type { Component } from "solid-js";
import { useToast } from "solid-notifications";

const SignInPage: Component = () => {
  const [form, { Form, Field }] = createForm<ISignInForm>();
  const { notify } = useToast();

  const handleSubmit: SubmitHandler<ISignInForm> = async (values, event) => {
    console.log("values", values);
    console.log("event", event);

    try {
      const res = await signInApi(values.username, values.password);
      console.log("res", res);
    } catch (err) {
      notify((err as Error).message, { type: "error" });
    }
  };

  return (
    <section class="flex-1 flex items-center justify-center">
      <div class="mx-4 w-full md:w-1/3">
        <Card title="Sign In to your account">
          <Form onSubmit={handleSubmit}>
            <fieldset class="grid grid-cols-1 gap-4" disabled={form.submitting}>
              <Field
                name="username"
                validate={[required("Username is required!")]}>
                {(field, attrs) => (
                  <FieldText
                    type="text"
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
                    type="password"
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
    </section>
  );
};

export default SignInPage;

interface ISignInForm extends FieldValues {
  username: string;
  password: string;
}

interface ISignInRes {
  token: {
    access_token: string;
    refresh_token: string;
    token_type: string;
    expires_in: number;
  };
}

async function signInApi(username: string, password: string) {
  const res = await fetch("/api/v1/auth/sign-in", {
    method: "POST",
    headers: { Accept: "application/json", "Content-Type": "application/json" },
    body: JSON.stringify({ username, password }),
  });

  if (!res.ok) {
    const resBody = await res.text();
    throw new Error(resBody);
  }

  return (await res.json()) as ISignInRes;
}
