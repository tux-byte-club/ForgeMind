# Forge Mind

A JavaFX desktop application for local LLM chats, with integrated Google Drive synchronization for chat history and a powerful web search feature.

---

### About the Project

**Forge Mind** is a desktop application designed to provide a secure and private AI chat experience. Unlike web-based solutions, this app runs large language models (LLMs) locally on your machine, ensuring your data never leaves your computer. With a sleek user interface built with JavaFX, it seamlessly integrates with Google Drive to back up your conversations, offering both privacy and convenience.

*Code Name: Project Plasma*

### Key Features

* **Local LLM Integration:** Runs popular LLM models like Phi-2 2.7B GGUF or Mistral-7B GGUF directly on your machine using the `llama.cpp` C++ library via JNI.
* **One-Time Local Downloads:** Automatically downloads the required LLM models the first time the app is run.
* **Google Drive Synchronization:** Securely authenticates with Google Drive to store and retrieve your chat history, making your data accessible and backed up.
* **Flexible Chat Modes:** Offers three distinct modes to suit your needs:
    * **Normal Chat:** Saved and synced to Google Drive.
    * **Temporary Chat:** Private conversations that are not saved.
    * **Web Search Chat:** Integrates with a search API to provide up-to-date, factual information.
* **Modern UI:** A clean and intuitive user interface built with the JavaFX framework.

### Technologies Used

* **Front-End:** JavaFX
* **Back-End:** Java
* **AI Engine:** `llama.cpp` (via JNI)
* **Cloud Storage:** Google Drive API for Java

### Getting Started

To get a local copy up and running, follow these simple steps:

1.  Clone the repository:
    ```sh
    git clone https://github.com/tux-byte-club/ForgeMind.git
    ```
2.  Open the project in your favorite Java IDE (e.g., IntelliJ IDEA, Eclipse).
3.  Configure JNI to work with your local `llama.cpp` build.
4.  Run the application. The LLM models will be downloaded automatically on the first run.

### Roadmap

Here's the plan for getting **Forge Mind** up and running. This checklist will be updated as we make progress!

- [ ] **Phase 1: Core Functionality**
    - [ ] Set up basic JavaFX UI with a chat window.
    - [ ] Implement Java Native Interface (JNI) to call a `llama.cpp` function.
    - [ ] Successfully load and run a simple LLM model.
    - [ ] Implement basic chat functionality (send input, receive output).

- [ ] **Phase 2: Data & Storage**
    - [ ] Implement Google Drive API integration.
    - [ ] Handle OAuth 2.0 authentication for the user's Google account.
    - [ ] Create methods to save and load chat history from Drive.
    - [ ] Create a local cache for chat history.

- [ ] **Phase 3: Features**
    - [ ] Implement the "Normal Chat" mode with Google Drive saving.
    - [ ] Implement the "Temporary Chat" mode that doesn't save history.
    - [ ] Implement the "Web Search" functionality and feed results to the LLM.
    - [ ] Add a visual download progress bar for the first-time model download.

- [ ] **Phase 4: Refinement & UX**
    - [ ] Improve the chat UI with timestamps and user/AI labels.
    - [ ] Add error handling for network issues, API errors, etc.
    - [ ] Optimize model loading and performance.
    - [ ] Prepare for release!

---

### System Requirements

To ensure a smooth and efficient experience, your system should meet the following minimum requirements. Performance will scale directly with your hardware, especially your RAM and CPU.

* **Operating System:** Windows 10/11, macOS, or a modern Linux distribution.
* **RAM:**
    * **Minimum:** **8 GB** is required for the application and the smaller LLM model (e.g., Phi-2 2.7B).
    * **Recommended:** **16 GB** or more is highly recommended for running larger models (e.g., Mistral-7B) without performance issues.
* **Processor (CPU):** A modern multi-core processor is essential, as `llama.cpp` is heavily optimized for CPU usage.
* **Storage:** At least **10-15 GB** of free disk space is needed to download and store the application and the LLM model files.
* **Java Runtime:** **Java 17** or a more recent version of the JRE/JDK is required to run the application.

---

## License

This project is released under the [**MIT License**](./LICENSE).

---
