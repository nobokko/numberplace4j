import './np-cell.js'

customElements.define('np-container', class extends HTMLElement {
    static get observedAttributes() {
        return [
        ];
    }

    constructor() {
        super();

        const template_style = `
            <style>
                ::slotted([slot="container"]) {
                    display :grid;
                    grid-template-columns: repeat(9, 2rem);
                    grid-template-rows: repeat(9, 2rem);
                }
            </style>
        `.replace(/\n/g, '').replace(/ {2,}/g, ' ');
        const template_body = `
            <slot name="container"></slot>
        `.replace(/\n/g, '').replace(/ {2,}/g, ' ');

        const shadow_root = this.attachShadow({ mode: 'open' });
        shadow_root.innerHTML = template_style + template_body;

        const owner_form = this.closest('form');
        if (owner_form) {
            this.querySelectorAll('[slot="container"] np-cell').forEach(ele => {
                console.debug(ele);
                owner_form.appendChild(ele.getInputHiddenTag());
            });
        }
    }

    attributeChangedCallback(name, oldValue, newValue) {
    }
});