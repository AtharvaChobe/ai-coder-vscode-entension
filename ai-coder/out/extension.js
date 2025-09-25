"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.activate = activate;
exports.deactivate = deactivate;
const vscode = require("vscode");
const axios_1 = require("axios");
const BACKEND_URL = 'http://localhost:8080/generate'; // change when deployed
function activate(context) {
    // Status bar button
    // const aiButton = vscode.window.createStatusBarItem(vscode.StatusBarAlignment.Right, 100);
    // aiButton.text = "$(rocket) Spring AI";
    // aiButton.tooltip = "Generate Spring Boot Code with AI";
    // aiButton.command = "ai-coder.generateCode";
    // aiButton.show();
    // Register command
    let disposable = vscode.commands.registerCommand("ai-coder.generateCode", async function () {
        const userInput = await vscode.window.showInputBox({
            prompt: "Enter your request (e.g. Generate CRUD controller)"
        });
        if (!userInput)
            return;
        // Ask user to pick up to 3 context files
        const files = await vscode.window.showOpenDialog({
            canSelectMany: true,
            openLabel: "Select Context Files (max 3)",
            filters: { "Java": ["java"], "TypeScript": ["ts"], "All Files": ["*"] }
        });
        const contextFiles = [];
        if (files) {
            for (const file of files.slice(0, 3)) {
                const doc = await vscode.workspace.openTextDocument(file);
                contextFiles.push({
                    name: doc.fileName.split("/").pop() || "unknown",
                    content: doc.getText()
                });
            }
        }
        // Call backend with prompt + selected context
        await vscode.window.withProgress({
            location: vscode.ProgressLocation.Notification,
            title: "Generating Spring AI code…",
            cancellable: false
        }, async () => {
            try {
                const res = await axios_1.default.post(BACKEND_URL, {
                    prompt: userInput + " , dont add any comments and docs",
                    contextFiles
                });
                const generated = res.data.generatedCode || JSON.stringify(res.data);
                const editor = vscode.window.activeTextEditor;
                if (editor) {
                    editor.edit(edit => edit.insert(editor.selection.active, `\n${generated}\n`));
                }
                else {
                    const doc = await vscode.workspace.openTextDocument({ language: 'java', content: generated });
                    await vscode.window.showTextDocument(doc);
                }
                vscode.window.showInformationMessage('✅ AI code generated and inserted.');
            }
            catch (err) {
                vscode.window.showErrorMessage('❌ Error calling backend: ' + (err.message || err));
            }
        });
    });
    // context.subscriptions.push(aiButton);
    context.subscriptions.push(disposable);
}
function deactivate() { }
//# sourceMappingURL=extension.js.map