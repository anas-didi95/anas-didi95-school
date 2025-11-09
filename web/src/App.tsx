import type { Component } from "solid-js";
import Button from "./components/Button";
import Card from "./components/Card";
import FieldText from "./components/FieldText";

const App: Component = () => {
  return (
    <div class="min-h-screen flex items-center justify-center bg-base-300">
      <div class="mx-4 w-full md:w-1/3">
        <Card title="Sign In to your account">
          <form>
            <fieldset class="grid grid-cols-1 gap-4">
              <FieldText label="Username" />
              <FieldText label="Password" />
            </fieldset>
            <div class="mt-4 flex justify-end gap-2">
              <Button label="Sign In" color="primary" />
            </div>
          </form>
        </Card>
      </div>
    </div>
  );
};

export default App;
