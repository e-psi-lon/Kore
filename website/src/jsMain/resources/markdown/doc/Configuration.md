---
root: .components.layouts.MarkdownLayout
title: Configuration
nav-title: Configuration
description: A guide for configuring the output of a generated Minecraft datapack.
keywords: minecraft, datapack, kore, guide, configuration
date-created: 2024-04-06
date-modified: 2024-04-06
routeOverride: /docs/configuration
---

# DataPack Configuration

The `configuration` function allows configuring the output of the generated datapack.

## Example

```kotlin
dataPack("mypack") {
	configuration {
		prettyPrint = true
		prettyPrintIndent = "  "
	}

	// ... rest of datapack code
}
```

This will configure the JSON output to be pretty printed with two spaces for indentation.

The available configuration options are:

- `prettyPrint` - Whether to pretty print the JSON. Default is `false`.
- `prettyPrintIndent` - The string to use for indenting when pretty printing. Only whitespace characters are allowed. Default is empty
  string.
- `generatedFunctionsFolder` - The folder where the generated functions are stored. Defaults to `"generated_scopes"`.

Configuring a datapack is pretty useful for debugging.
