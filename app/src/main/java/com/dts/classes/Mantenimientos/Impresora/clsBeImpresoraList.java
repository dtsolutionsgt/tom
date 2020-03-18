package com.dts.classes.Mantenimientos.Impresora;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeImpresoraList {
    @ElementList(inline=true,required=false)
    public List<clsBeImpresora> items = new List<clsBeImpresora>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<clsBeImpresora> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(clsBeImpresora clsBeImpresora) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends clsBeImpresora> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeImpresora> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public clsBeImpresora get(int index) {
            return null;
        }

        @Override
        public clsBeImpresora set(int index, clsBeImpresora element) {
            return null;
        }

        @Override
        public void add(int index, clsBeImpresora element) {

        }

        @Override
        public clsBeImpresora remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<clsBeImpresora> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeImpresora> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeImpresora> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}
